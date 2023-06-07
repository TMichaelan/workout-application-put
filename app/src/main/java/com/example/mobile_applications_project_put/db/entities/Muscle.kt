package com.example.mobile_applications_project_put.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscles")
data class Muscle(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val imageUrl: String
)