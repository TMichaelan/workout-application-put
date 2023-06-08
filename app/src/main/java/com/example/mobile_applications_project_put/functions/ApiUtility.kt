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

    fun getBodyParts(){

        RetrofitInstance.excerciseAPI.getBodyPartsList().enqueue(object : Callback<BodyPartsList> {

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val bodyParts = response.body()
                Log.d("getBodyParts", "Body part:${bodyParts}")
                if (bodyParts != null) {
                    // Вывод данных
                    for (part in bodyParts) {
                        Log.d("getBodyParts", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getBodyParts", "Error: ${t.message}")
            }
        })

    }

    fun getTargetList(){

        RetrofitInstance.excerciseAPI.getTargetPartsList().enqueue(object :  Callback<BodyPartsList>{

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val targetParts = response.body()

                Log.d("getTargetList", "Body part:${targetParts}")
                if (targetParts != null) {
                    // Вывод данных
                    for (part in targetParts) {
                        Log.d("getTargetList", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getTargetList", "Error: ${t.message}")
            }
        })

    }

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

    fun getExerciseListByName(exersiceName : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByName(exersiceName).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("getExerciseListByName", "Response received for body part: $exersiceName")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("getExerciseListByName", "Body part: $exercise")
                    }
                } else {
                    Log.d("getExerciseListByName", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("getExerciseListByName", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseById(exersiceId : String) {
        RetrofitInstance.excerciseAPI.getExerciseIdById(exersiceId).enqueue(object :  Callback<ExerciseItem>{

            override fun onResponse(call: Call<ExerciseItem>, response: Response<ExerciseItem>) {
                val exerciseItem = response.body()
                Log.d("getExerciseById", "Response received for exercise ID: $exersiceId")
                if (exerciseItem != null) {
                    // Print data
                    Log.d("getExerciseById", "Exercise item: $exerciseItem")

                } else {
                    Log.d("getExerciseById", "No exercise item returned in the response")
                }
            }

            override fun onFailure(call: Call<ExerciseItem>, t: Throwable) {
                Log.e("getExerciseById", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseListByTarget(target : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByTarget(target).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("getExerciseListByTarget", "Response received for body part: $target")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("getExerciseListByTarget", "Body part: $exercise")
                    }
                } else {
                    Log.d("getExerciseListByTarget", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("getExerciseListByTarget", "Error: ${t.message}")
            }
        })
    }



}