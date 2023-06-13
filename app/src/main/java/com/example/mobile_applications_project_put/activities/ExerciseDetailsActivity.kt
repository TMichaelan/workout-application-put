package com.example.mobile_applications_project_put.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.fragments.HomeFragment
import com.example.mobile_applications_project_put.functions.DbUtility
import com.example.mobile_applications_project_put.functions.DbUtility.loadSavedExercises
import com.example.mobile_applications_project_put.functions.UserUtility.isInternetAvailable
import kotlinx.coroutines.*

class ExerciseDetailsActivity : AppCompatActivity() {
    private lateinit var exerciseNameText: String
    private lateinit var bodyPartText: String
    private lateinit var equipmentText: String
    private lateinit var id: String
    private lateinit var targetText: String
    private lateinit var gifImage: String

    private fun getExerciseInformationFromIntent() {
        val intent = intent

        if (intent.getStringExtra("callingActivity") == "ExerciseListActivity") {

            bodyPartText = intent.getStringExtra(ExerciseListActivity.BODYPART)!!
            exerciseNameText = intent.getStringExtra(ExerciseListActivity.NAME)!!
            equipmentText = intent.getStringExtra(ExerciseListActivity.EQUIPMENT)!!
            id = intent.getStringExtra(ExerciseListActivity.ID)!!
            targetText = intent.getStringExtra(ExerciseListActivity.TARGET)!!
            gifImage = intent.getStringExtra(ExerciseListActivity.GIFURL)!!
        }
        else if (intent.getStringExtra("callingActivity") == "LocalWorkoutActivity"){
            bodyPartText = intent.getStringExtra(LocalWorkoutActivity.BODYPART)!!
            exerciseNameText = intent.getStringExtra(LocalWorkoutActivity.NAME)!!
            equipmentText = intent.getStringExtra(LocalWorkoutActivity.EQUIPMENT)!!
            id = intent.getStringExtra(LocalWorkoutActivity.ID)!!
            targetText = intent.getStringExtra(LocalWorkoutActivity.TARGET)!!
            gifImage = intent.getStringExtra(LocalWorkoutActivity.GIFURL)!!
        } else {
            bodyPartText = intent.getStringExtra(HomeFragment.BODYPART)!!
            exerciseNameText = intent.getStringExtra(HomeFragment.NAME)!!
            equipmentText = intent.getStringExtra(HomeFragment.EQUIPMENT)!!
            id = intent.getStringExtra(HomeFragment.ID)!!
            targetText = intent.getStringExtra(HomeFragment.TARGET)!!
            gifImage = intent.getStringExtra(HomeFragment.GIFURL)!!
        }
    }


    @SuppressLint("SetTextI18n")
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

        val internet = isInternetAvailable(this)

        if (internet) {
            Glide.with(this)
                .load(gifImage)
                .into(gifImageView)

            val save: TextView = findViewById(R.id.addExercise)
            save.setOnClickListener {

                if (save.text == "♡")
                {
                    DbUtility.dbAddExerciseById(this, id)
                    save.setText("♥")
                }else
                {
                    save.setText("♡")
                    DbUtility.dbRemoveExerciseById(this, id)
                }
            }

            CoroutineScope(Dispatchers.Main).async {
                val exercises = loadSavedExercises(this@ExerciseDetailsActivity)
                for (i in exercises) {
                    if (i.name == exerciseNameText) {
                        save.setText("♥")
                        Log.d("HEREID", "i.id: ${i.name}, id:$exerciseNameText")
                    }
                }
                Log.d("exercisessss", "$exercises")
            }

        }else{
            Glide.with(this)
                .load(R.drawable.no_inet)
                .into(gifImageView)

            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show()

        }

    }


}

