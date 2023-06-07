package com.example.mobile_applications_project_put.retrofit


//import com.example.mobile_applications_project_put.models.CategoryList
import com.example.mobile_applications_project_put.models.BodyPartExcerciseList
import com.example.mobile_applications_project_put.models.BodyPartsList
import com.example.mobile_applications_project_put.models.ExerciseItem
//import com.example.mobile_applications_project_put.models.ExcerciseList

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ExcerciseAPI {

    //body parts like back, chest, ...
    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("bodyPartList")
    fun getBodyPartsList(): Call<BodyPartsList>

    //Doesn't work because of the API, too much data to load
    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("exercises")
    fun getAllExercisesList(): Call<BodyPartsList>

    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("bodyPart/{bodyPart}")
    fun getBodyPart(@Path("bodyPart") bodyPart: String): Call<BodyPartExcerciseList>

    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("exercise/{exerciseId}")
    fun getExerciseIdById(@Path("exerciseId") exerciseId: String): Call<ExerciseItem>

    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("name/{exerciseName}")
    fun getExerciseListByName(@Path("exerciseName") exerciseId: String): Call<BodyPartExcerciseList>

    //targets like: abs, biceps, triceps, ...
    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("targetList")
    fun getTargetPartsList(): Call<BodyPartsList>

    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("target/{target}")
    fun getExerciseListByTarget(@Path("target") exerciseId: String): Call<BodyPartExcerciseList>

}
