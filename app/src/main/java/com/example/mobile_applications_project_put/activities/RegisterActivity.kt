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

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

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
            } else {
                val user = User(username, email, password)

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
                FirebaseUtility.loginUser(email, password) { success, username, message ->
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


