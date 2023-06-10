package com.example.mobile_applications_project_put.db.entities

data class User(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    val name: String? = null
)