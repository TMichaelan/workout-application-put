package com.example.mobile_applications_project_put.activities

import LocalExerciseListAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.AppDatabase
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.GifEntity
import com.example.mobile_applications_project_put.functions.DbUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LocalExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseAdapter: LocalExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_exercise)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        exerciseAdapter = LocalExerciseListAdapter(this)
        recyclerView.adapter = exerciseAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val exercises = loadSavedExercises(this@LocalExerciseActivity)
            withContext(Dispatchers.Main) {
                exerciseAdapter.setExercises(exercises)
            }
        }

    }

//    private suspend fun loadSavedExercises(context: Context): List<Exercise> {
//        val savedExercises = AppDatabase.getInstance(context).exerciseDao().getAllExercises()
//
//        savedExercises.forEach {
//            Log.d("LOADSAVEDEXERCISES", "Saved exercise: $it")
//        }
//
//        return savedExercises
//    }

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

}

