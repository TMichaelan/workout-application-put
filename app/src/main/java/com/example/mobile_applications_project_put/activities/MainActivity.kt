package com.example.mobile_applications_project_put.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.TabsPageAdapter
import com.example.mobile_applications_project_put.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Number Of Tabs
        val numberOfTabs = 4

        // Set the ViewPager Adapter
        val adapter = TabsPageAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter

        // Enable Swipe
        binding.tabsViewpager.isUserInputEnabled = true

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "1"
                    tab.setIcon(R.drawable.ic_launcher_background)
                }
                1 -> {
                    tab.text = "2"
                    tab.setIcon(R.drawable.ic_launcher_foreground)

                }
                2 -> {
                    tab.text = "3"
                    tab.setIcon(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                }
                3 -> {
                    tab.text = "4"
                    tab.setIcon(androidx.appcompat.R.drawable.abc_btn_check_material)
                }
            }
        }.attach()
    }
}
