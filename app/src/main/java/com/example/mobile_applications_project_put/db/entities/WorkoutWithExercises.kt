package com.example.mobile_applications_project_put.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutWithExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",  // This is the ID of Workout
        entityColumn = "id",  // This is the ID of Exercise
        associateBy = Junction(
            value = WorkoutExerciseCrossRef::class,
            parentColumn = "workoutId",  // This is the workoutId in WorkoutExerciseCrossRef
            entityColumn = "exerciseId"  // This is the exerciseId in WorkoutExerciseCrossRef
        )
    )
    val exercises: List<Exercise>
)