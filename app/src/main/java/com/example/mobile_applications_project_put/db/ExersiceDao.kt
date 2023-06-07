package com.example.mobile_applications_project_put.db

import com.example.mobile_applications_project_put.db.entities.Exercise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mobile_applications_project_put.models.ExerciseItem

@Dao
interface ExersiceDao {

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): List<Exercise>

    @Insert
    fun insert(exercise: Exercise)

    @Query("SELECT * FROM exercise WHERE id = :idExercise")
    fun getExerciseById(idExercise: String): Exercise?

    @Delete
    fun deleteExercise(exercise: Exercise)

}