package com.example.mobile_applications_project_put.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Insert

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey var id: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val name: String,
    val target: String
) {
    constructor() : this("", "", "", "", "", "")
}