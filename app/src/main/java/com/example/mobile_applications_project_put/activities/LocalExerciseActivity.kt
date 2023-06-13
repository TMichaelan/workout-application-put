package com.example.mobile_applications_project_put.activities

import LocalExerciseListAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
//import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.AppDatabase
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.GifEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LocalExerciseActivity : AppCompatActivity(), LocalExerciseListAdapter.OnItemClickListener {

    private lateinit var exerciseAdapter: LocalExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_exercise)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        exerciseAdapter = LocalExerciseListAdapter(this, this)
        recyclerView.adapter = exerciseAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val exercises = loadSavedExercises(this@LocalExerciseActivity)
            withContext(Dispatchers.Main) {
                exerciseAdapter.setExercises(exercises)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val exercises = loadSavedExercises(this@LocalExerciseActivity)
            withContext(Dispatchers.Main) {
                exerciseAdapter.setExercises(exercises)
            }
        }
    }
    override fun onItemClick(exercise: Exercise) {
        val intent = Intent(this, ExerciseDetailsActivity::class.java)

        intent.putExtra("callingActivity", "ExerciseListActivity")

        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)
        intent.putExtra(ID, exercise.id)
        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)

        startActivity(intent)
    }

    private suspend fun loadSavedExercises(context: Context): List<Pair<Exercise, GifEntity?>> {
        val database = AppDatabase.getInstance(context)
        val exerciseDao = database.exerciseDao()
        val gifDao = database.gifDao()
        val savedExercises = exerciseDao.getAllExercises()

        val exerciseWithGifList = mutableListOf<Pair<Exercise, GifEntity?>>()
        for (exercise in savedExercises) {
            val gifEntity = gifDao.getGifById(exercise.id)
            exerciseWithGifList.add(Pair(exercise, gifEntity))
        }

        return exerciseWithGifList
    }
    companion object{
        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
        const val ID = "com.example.mobile_applications_project_put.fragments.id"
        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
    }
}

