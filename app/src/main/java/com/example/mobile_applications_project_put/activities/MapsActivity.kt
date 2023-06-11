package com.example.mobile_applications_project_put.activities

import MapsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_applications_project_put.R

class MapsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val fragment = MapsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}