package com.example.mobile_applications_project_put.db.entities

data class User(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var name: String? = null,
    var height: Int? = null,
    var age: Int? = null,
    var weight: Double? = null
)

