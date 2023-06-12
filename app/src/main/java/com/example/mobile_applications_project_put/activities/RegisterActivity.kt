package com.example.mobile_applications_project_put.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_applications_project_put.databinding.ActivityRegisterBinding
import com.example.mobile_applications_project_put.db.entities.User
import com.example.mobile_applications_project_put.functions.FirebaseUtility
import java.security.MessageDigest


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashedPassword = md.digest(password.toByteArray())
        return hashedPassword.fold("", { str, it -> str + "%02x".format(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            }else if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(password)) {
                Toast.makeText(this, "Please enter a password with at least 6 characters", Toast.LENGTH_SHORT).show()
            } else  {
                val hashedPassword = hashPassword(password)
                val user = User(username, email, hashedPassword)

                FirebaseUtility.registerUser(user) { success, message ->
                    if (success) {
                        Log.d("RegisterActivity", "Registration successful.")
                        val sharedPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString("username", username)
                            putBoolean("logged_in", true)
                            apply()
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("RegisterActivity", "Registration failed: $message")
                        Toast.makeText(this, "Registration failed: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val hashedPassword = hashPassword(password)
                FirebaseUtility.loginUser(email, hashedPassword) { success, username, message ->
                    if (success) {
                        Log.d("RegisterActivity", "Login successful.")
                        val sharedPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString("username", username)
                            putBoolean("logged_in", true)
                            apply()
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("RegisterActivity", "Login failed: $message")
                        Toast.makeText(this, "Login failed: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


