package com.example.mobile_applications_project_put.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.databinding.ActivityAddToWorkoutBodyPartListBinding
import com.example.mobile_applications_project_put.fragments.BodyPartsListFragment

class AddToWorkoutBodyPartListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddToWorkoutBodyPartListBinding
    private lateinit var username:String
    private lateinit var workoutId: String
    private lateinit var workoutName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("username").toString()
        workoutId = intent.getStringExtra("workoutId").toString()
        workoutName = intent.getStringExtra("workoutName").toString()
        Log.d("UserWorkout", "username: $username, workout: $workoutId")

        binding = ActivityAddToWorkoutBodyPartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bodyPartsListFragment = BodyPartsListFragment.newInstance("AddToWorkoutBodyPartListActivity", username, workoutId, workoutName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, bodyPartsListFragment)
            .commit()

        setResult(RESULT_OK, intent)
    }

}
