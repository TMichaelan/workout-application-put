package com.example.mobile_applications_project_put.retrofit


//import com.example.mobile_applications_project_put.models.CategoryList
import com.example.mobile_applications_project_put.models.BodyPartsList
//import com.example.mobile_applications_project_put.models.ExcerciseList

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface ExcerciseAPI {
    @Headers("X-RapidAPI-Key:99a7fbd93fmsh80f07a9a0d05076p1761dajsnae3e7accd5d2")
    @GET("bodyPartList")
    fun getBodyParts(): Call<BodyPartsList>



//    @GET("categories.php")
//    fun getCategories(): Call<CategoryList>

//    @GET("lookup.php?")
//    fun getMealById(@Query("i") id:String): Call<MealList>

//    @GET("search.php?")
//    fun getMealByName(@Query("s") s:String): Call<MealList>
}