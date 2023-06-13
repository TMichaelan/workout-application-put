package com.example.mobile_applications_project_put.functions

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobile_applications_project_put.db.AppDatabase
import com.example.mobile_applications_project_put.db.GifDao
import com.example.mobile_applications_project_put.db.entities.*
import com.example.mobile_applications_project_put.models.ExerciseItem
import com.example.mobile_applications_project_put.retrofit.RetrofitInstance
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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

                    CoroutineScope(Dispatchers.IO).launch {
                        val database = AppDatabase.getInstance(context)
                        val gifDao = database.gifDao()

                        // Call downloadAndStoreGif function
                        downloadAndStoreGif(exerciseItem.gifUrl, exerciseItem.id, gifDao)

                        // Convert ExerciseItem to Exercise
                        val exercise = Exercise(
                            id = exerciseItem.id,
                            bodyPart = exerciseItem.bodyPart,
                            equipment = exerciseItem.equipment,
                            gifUrl = exerciseItem.gifUrl,
                            name = exerciseItem.name,
                            target = exerciseItem.target
                        )

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

    suspend fun loadSavedExercises(context: Context): List<Exercise> {
        return withContext(Dispatchers.IO) {
            val savedExercises = AppDatabase.getInstance(context).exerciseDao().getAllExercises()

            savedExercises.forEach {
                Log.d("LOADSAVEDEXERCISES", "Saved exercise: $it")
            }

            savedExercises
        }
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

//    fun loadWorkouts(context: Context): List<Workout> {
//        var workouts: List<Workout> = listOf()
//        coroutineScope.launch {
//            workouts = withContext(Dispatchers.IO) {
//                AppDatabase.getInstance(context).workoutDao().getAllWorkouts()
//            }
//            for (workout in workouts) {
//                Log.d("loadWorkouts", "Saved workouts: $workout")
//            }
//        }
//        return workouts
//    }
suspend fun loadWorkouts(context: Context): List<Workout> {
    return withContext(Dispatchers.IO) {
        val workouts = AppDatabase.getInstance(context).workoutDao().getAllWorkouts()
        for (workout in workouts) {
            Log.d("loadWorkouts", "Saved workouts: $workout")
        }
        workouts
    }
}


    fun loadWorkoutWithExercises(context: Context, workoutId: Int) =
        AppDatabase.getInstance(context).workoutWithExercisesDao().getWorkoutWithExercises(workoutId)

    fun deleteWorkout(context: Context, workoutId: Int) {
        coroutineScope.launch {
            val workoutDao = AppDatabase.getInstance(context).workoutDao()
            val workout = workoutDao.getWorkoutById(workoutId)
            if (workout != null) {
                workoutDao.deleteWorkout(workout)
                Log.d("Workout Deletion", "Deleted workout with ID: $workoutId")
            } else {
                Log.e("Workout Deletion", "Failed to delete workout: No workout found with ID: $workoutId")
            }
        }
    }

    fun downloadAndStoreGif(url: String, exerciseId: String, dao: GifDao) {
        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.byteStream()?.let { inputStream ->
                    val gifByteArray = inputStream.readBytes()
                    val gifEntity = GifEntity(exerciseId, gifByteArray)
                    GlobalScope.launch(Dispatchers.IO) {
                        dao.addGif(gifEntity)
                    }
                }
            }
        })
    }

    fun dbRemoveExerciseById(context: Context, exerciseId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getInstance(context)
            val gifDao = database.gifDao()


            val exerciseInDb = database.exerciseDao().getExerciseById(exerciseId)
            val gifInDb = gifDao.getGifById(exerciseId)
            if (exerciseInDb != null) {
                database.exerciseDao().deleteExercise(exerciseInDb)
                Log.d("dbRemoveExerciseById", "Exercise removed: $exerciseInDb")

                if (gifInDb != null) {
                    gifDao.deleteGifById(exerciseId)
                } else {
                    Log.d("dbRemoveExerciseById", "Gif not found with ID: $exerciseId")
                }

            } else {
                Log.d("dbRemoveExerciseById", "Exercise not found with ID: $exerciseId")
            }
        }
    }


}
