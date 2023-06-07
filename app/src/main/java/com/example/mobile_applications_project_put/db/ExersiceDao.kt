package com.example.mobile_applications_project_put.db

import com.example.mobile_applications_project_put.db.entities.Exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExersiceDao {

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): List<Exercise>

    @Insert
    fun insert(exercise: Exercise)
}