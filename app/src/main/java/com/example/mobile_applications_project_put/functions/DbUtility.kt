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
//                        gifUrl = exerciseItem.gifUrl,
                        gifUrl = "https://storage.googleapis.com/mobile-app-b1065.appspot.com/gifs/0071.gif?Expires=1689189573&GoogleAccessId=firebase-adminsdk-q53ns%40mobile-app-b1065.iam.gserviceaccount.com&Signature=ZAfZN3z8lBIJEPehR8W6p3STHpdrP7B%2BT56NgkB90%2BAEVM3YJpMYmff%2BEZV9%2BzxOxKAdyk9ak%2Fj0OXuS9BCELFPcgKa3Cc0riwAPRw%2Fl8Lwx8HoNF%2Fv8stEplbPGBo8ZrK%2F8H5TB%2B%2FZDNDtTnWZmRW3%2FzgTe%2BxxLGSvJjU4GVEJ2O%2B%2FzAjyYhrdxkf5wBeGbXLJwFNY9zjMUfAjQLdJqoSO5QfiD%2F9yQRN8aDj%2FAokrQY613w75dGnG82seWvLX09jmxbSJpKM7b0Ppne5MqaWruuhVxY57Yz%2F%2BdaBIC0q8F8j0xFokkpO%2F6IrMN6MGZMkQLixbslGI%2B8qXHtBUU6A%3D%3D",
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

}
