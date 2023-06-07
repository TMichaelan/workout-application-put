package com.example.mobile_applications_project_put.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobile_applications_project_put.db.entities.Muscle

@Dao
interface MuscleDao {
    @Insert
    fun insert(muscle: Muscle)

    @Query("SELECT * FROM muscles")
    fun getAll(): List<Muscle>
}