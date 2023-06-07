package com.example.mobile_applications_project_put.functions

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobile_applications_project_put.db.AppDatabase
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutExerciseCrossRef
import com.example.mobile_applications_project_put.db.entities.WorkoutWithExercises
import com.example.mobile_applications_project_put.models.ExerciseItem
import com.example.mobile_applications_project_put.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DbUtility {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun deleteAppDatabase(context: Context) {
        context.deleteDatabase("exercises_db")
        Log.d("DATABASE", "Database deleted successfully")
    }

    fun dbAddExerciseById(context: Context, exersiceId: String) {
        RetrofitInstance.excerciseAPI.getExerciseIdById(exersiceId).enqueue(object :
            Callback<ExerciseItem> {

            override fun onResponse(call: Call<ExerciseItem>, response: Response<ExerciseItem>) {
                val exerciseItem = response.body()
                Log.d("dbAddExerciseById", "Response received for exercise ID: $exersiceId")
                if (exerciseItem != null) {

                    Log.d("dbAddExerciseById", "Exercise item: $exerciseItem")

                    // Convert ExerciseItem to Exercise
                    val exercise = Exercise(
                        id = exerciseItem.id,
                        bodyPart = exerciseItem.bodyPart,
                        equipment = exerciseItem.equipment,
                        gifUrl = exerciseItem.gifUrl,
                        name = exerciseItem.name,
                        target = exerciseItem.target
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val database = AppDatabase.getInstance(context)
                        val exerciseInDb = database.exerciseDao().getExerciseById(exercise.id)
                        if (exerciseInDb != null) {
                            database.exerciseDao().deleteExercise(exerciseInDb)
                        } else {
                            database.exerciseDao().insert(exercise)
                        }
                    }

                } else {
                    Log.d("dbAddExerciseById", "No exercise item returned in the response")
                }
            }

            override fun onFailure(call: Call<ExerciseItem>, t: Throwable) {
                Log.e("dbAddExerciseById", "Error: ${t.message}")
            }
        })
    }

    fun loadSavedExercises(context: Context): List<Exercise> {
        var savedExercises: List<Exercise> = listOf()

        CoroutineScope(Dispatchers.IO).launch {
            savedExercises = AppDatabase.getInstance(context).exerciseDao().getAllExercises()

            savedExercises.forEach {
                Log.d("LOADSAVEDEXERCISES", "Saved exercise: $it")
            }
        }

        return savedExercises
    }

    fun createWorkout(context: Context, name: String) {
        val newWorkout = Workout(name = name, id = 0)

        CoroutineScope(Dispatchers.IO).launch {
            val workoutId = AppDatabase.getInstance(context).workoutDao().insertWorkout(newWorkout)
            Log.d("Workout Creation", "Created workout with ID: $workoutId")
        }
    }
    fun addExerciseToWorkout(context: Context, workoutId: Int, exerciseId: String) {
        val crossRef = WorkoutExerciseCrossRef(workoutId, exerciseId)

        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).workoutWithExercisesDao().insertWorkoutExerciseCrossRef(crossRef)
            Log.d("Workout Creation", "Added exercise with ID: $exerciseId to workout with ID: $workoutId")
        }
    }

    fun loadWorkouts(context: Context): List<Workout> {
        var workouts: List<Workout> = listOf()
        coroutineScope.launch {
            workouts = withContext(Dispatchers.IO) {
                AppDatabase.getInstance(context).workoutDao().getAllWorkouts()
            }
            for (workout in workouts) {
                Log.d("loadWorkouts", "Saved workouts: $workout")
            }
        }
        return workouts
    }

    fun loadWorkoutWithExercises(context: Context, workoutId: Int) =
        AppDatabase.getInstance(context).workoutWithExercisesDao().getWorkoutWithExercises(workoutId)
}
