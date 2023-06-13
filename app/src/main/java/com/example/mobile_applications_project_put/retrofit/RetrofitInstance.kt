package com.example.mobile_applications_project_put.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val excerciseAPI:ExcerciseAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com/exercises/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExcerciseAPI::class.java)
    }
}