package com.example.mobile_applications_project_put.db.entities

data class User(
    val username: String,
    val email: String,
    val password: String,
    val name: String? = null
)