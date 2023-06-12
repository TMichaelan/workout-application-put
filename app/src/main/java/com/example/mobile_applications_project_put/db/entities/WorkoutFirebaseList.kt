package com.example.mobile_applications_project_put.db.entities

data class WorkoutFirebaseList(
    var id: String? = null,
    val name: String = "",
    val exercises: List<Exercise> = listOf()
)