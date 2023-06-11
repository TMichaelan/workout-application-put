package com.example.mobile_applications_project_put.functions

import android.util.Log
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.models.*
import com.example.mobile_applications_project_put.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiUtility {

// moved to JSON

//    fun getBodyParts(){
//
//        RetrofitInstance.excerciseAPI.getBodyPartsList().enqueue(object : Callback<BodyPartsList> {
//
//            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
//                val bodyParts = response.body()
//                Log.d("getBodyParts", "Body part:${bodyParts}")
//                if (bodyParts != null) {
//                    // Вывод данных
//                    for (part in bodyParts) {
//                        Log.d("getBodyParts", "Body part: ${part}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
//                Log.e("getBodyParts", "Error: ${t.message}")
//            }
//        })
//
//    }

// moved to JSON

//    fun getTargetList(){
//        RetrofitInstance.excerciseAPI.getTargetPartsList().enqueue(object :  Callback<BodyPartsList>{
//
//            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
//                val targetParts = response.body()
//
//                Log.d("getTargetList", "Body part:${targetParts}")
//                if (targetParts != null) {
//                    // Вывод данных
//                    for (part in targetParts) {
//                        Log.d("getTargetList", "Body part: ${part}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
//                Log.e("getTargetList", "Error: ${t.message}")
//            }
//        })
//
//    }

// Doesn't work because of too much data

    fun getAllExercises() {
        RetrofitInstance.excerciseAPI.getAllExercisesList().enqueue(object :  Callback<BodyPartsList>{
            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val exercisesList = response.body()
                Log.d("getAllExercises", "Response received for all exercises: $exercisesList")

                if (exercisesList != null) {
                    // Вывод данных
                    for (exercise in exercisesList) {
                        Log.d("getAllExercises", "Exercise: $exercise")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getAllExercises", "Error: ${t.message}")
            }
        })
    }


    suspend fun getBodyPartExercises(bodyPart: String): List<BodyPartExcerciseListItem> {
        return withContext(Dispatchers.IO) {
            val call = RetrofitInstance.excerciseAPI.getBodyPart(bodyPart)
            val response = call.execute() // Synchronous network call
            if (response.isSuccessful) {
                val bodyParts = response.body()
                
                Log.d("getBodyPartExercises", "Response received for body part: ${bodyParts}")

                bodyParts ?: emptyList()
            } else {
                Log.d("getBodyPartExercises", "No body parts returned in the response")
                emptyList()
            }

        }
    }

    suspend fun getExerciseListByName(exersiceName: String): List<BodyPartExcerciseListItem> {
        return withContext(Dispatchers.IO) {
            val call = RetrofitInstance.excerciseAPI.getExerciseListByName(exersiceName)
            val response = call.execute() // Синхронный сетевой запрос
            if (response.isSuccessful) {
                val exercisesList = response.body()
                Log.d("getExerciseListByName", "Response received for body part: $exersiceName")
                exercisesList ?: emptyList()
            } else {
                Log.d("getExerciseListByName", "No body parts returned in the response")
                emptyList()
            }
        }
    }

    suspend fun getExerciseById(exerciseId: String): ExerciseItem? {
        return withContext(Dispatchers.IO) {
            try {
                val call = RetrofitInstance.excerciseAPI.getExerciseIdById(exerciseId)
                val response = call.execute() // Синхронный сетевой запрос
                if (response.isSuccessful) {
                    val exerciseItem = response.body()
                    Log.d("getExerciseById", "Response received for exercise ID: $exerciseId")
                    exerciseItem
                } else {
                    Log.d("getExerciseById", "No exercise item returned in the response")
                    null
                }
            } catch (e: Exception) {
                Log.e("getExerciseById", "Error: ${e.message}")
                null
            }
        }
    }
    suspend fun getExerciseListByTarget(target: String): BodyPartExcerciseList? {
        return withContext(Dispatchers.IO) {
            try {
                val call = RetrofitInstance.excerciseAPI.getExerciseListByTarget(target)
                val response = call.execute() // Синхронный сетевой запрос
                if (response.isSuccessful) {
                    val exercisesList = response.body()
                    Log.d("getExerciseListByTarget", "Response received for target: $target")
                    exercisesList
                } else {
                    Log.d("getExerciseListByTarget", "No body parts returned in the response")
                    null
                }
            } catch (e: Exception) {
                Log.e("getExerciseListByTarget", "Error: ${e.message}")
                null
            }
        }
    }

}