package com.example.mobile_applications_project_put.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mobile_applications_project_put.db.entities.Workout

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(workout: Workout): Long

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): List<Workout>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Int): Workout?

    @Delete
    suspend fun deleteWorkout(workout: Workout)

}