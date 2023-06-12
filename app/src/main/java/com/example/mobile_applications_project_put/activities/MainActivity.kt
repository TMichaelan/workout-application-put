package com.example.mobile_applications_project_put.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.TabsPageAdapter
import com.example.mobile_applications_project_put.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginTextView: TextView = binding.loginTextview
        loginTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val sharedPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)

        // if user is logged in, show username and logout textview
        if(username != null){
            binding.loginTextview.setOnClickListener(null)
            binding.loginTextview.text = username
            binding.logoutTextview.visibility = View.VISIBLE
        }

        // log out and reset login textview and hide logout textview
        val logoutTextView: TextView = binding.logoutTextview
        logoutTextView.setOnClickListener {
            with (sharedPref.edit()) {
                remove("username")
                putBoolean("logged_in", false) // mark the user as logged out
                apply()
            }
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show()
            binding.loginTextview.text = "Log In"
            binding.logoutTextview.visibility = View.GONE


            binding.loginTextview.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            // refresh the ViewPager Adapter to reflect the logged out state
            binding.tabsViewpager.adapter = TabsPageAdapter(this, supportFragmentManager, lifecycle)
        }

        // Set the ViewPager Adapter
        val adapter = TabsPageAdapter(this, supportFragmentManager, lifecycle)
        binding.tabsViewpager.adapter = adapter

        // Enable Swipe
        binding.tabsViewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.round_home_24)
                }
                1 -> {
                    tab.text = "Workouts"
                    tab.setIcon(R.drawable.round_sports_24)
                }
                2 -> {
                    tab.text = "Profile"
                    tab.setIcon(R.drawable.round_account_circle_24)
                }
//                3->{
//                    tab.text = "4"
//                }
            }
        }.attach()
    }
}

