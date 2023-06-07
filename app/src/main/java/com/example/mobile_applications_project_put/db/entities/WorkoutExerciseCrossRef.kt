package com.example.mobile_applications_project_put.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Insert

@Entity(primaryKeys = ["workoutId", "exerciseId"])
data class WorkoutExerciseCrossRef(
    val workoutId: Int,
    val exerciseId: String
)