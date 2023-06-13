package com.example.mobile_applications_project_put.db.entities

import androidx.room.Entity
@Entity(primaryKeys = ["workoutId", "exerciseId"])
data class WorkoutExerciseCrossRef(
    val workoutId: Int,
    val exerciseId: String
)