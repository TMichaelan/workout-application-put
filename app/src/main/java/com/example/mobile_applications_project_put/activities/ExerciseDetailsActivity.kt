package com.example.mobile_applications_project_put.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.fragments.HomeFragment

class ExerciseDetailsActivity : AppCompatActivity() {
    private lateinit var exerciseNameText: String
    private lateinit var bodyPartText: String
    private lateinit var equipmentText: String
    private lateinit var targetText: String
    private lateinit var gifImage: String

    private fun getExerciseInformationFromIntent() {
        val intent = intent

        if (intent.getStringExtra("callingActivity") == "ExerciseListActivity") {

            bodyPartText = intent.getStringExtra(ExerciseListActivity.BODYPART)!!
            exerciseNameText = intent.getStringExtra(ExerciseListActivity.NAME)!!
            equipmentText = intent.getStringExtra(ExerciseListActivity.EQUIPMENT)!!
            targetText = intent.getStringExtra(ExerciseListActivity.TARGET)!!
            gifImage = intent.getStringExtra(ExerciseListActivity.GIFURL)!!
        }
        else {
            bodyPartText = intent.getStringExtra(HomeFragment.BODYPART)!!
            exerciseNameText = intent.getStringExtra(HomeFragment.NAME)!!
            equipmentText = intent.getStringExtra(HomeFragment.EQUIPMENT)!!
            targetText = intent.getStringExtra(HomeFragment.TARGET)!!
//            gifImage = intent.getStringExtra(HomeFragment.GIFURL)!!
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_details)

        getExerciseInformationFromIntent()

        val nameTextView = findViewById<TextView>(R.id.exerciseNameTextView)
        val bodyPartTextView = findViewById<TextView>(R.id.bodyPartTextView)
        val equipmentTextView = findViewById<TextView>(R.id.equipmentTextView)
        val targetTextView = findViewById<TextView>(R.id.targetTextView)
        val gifImageView = findViewById<ImageView>(R.id.gifImageView)


        nameTextView.text = "Exercice: ${exerciseNameText.capitalize()}"
        bodyPartTextView.text = "Body Part: ${bodyPartText.capitalize()}"
        equipmentTextView.text = "Equipment: ${equipmentText.capitalize()}"
        targetTextView.text = "Target: ${targetText.capitalize()}"

        Glide.with(this)
            .load(gifImage)
            .into(gifImageView)

    }


}

