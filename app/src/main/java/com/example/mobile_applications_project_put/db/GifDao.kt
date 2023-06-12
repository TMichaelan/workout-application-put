package com.example.mobile_applications_project_put.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobile_applications_project_put.db.entities.GifEntity

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGif(gif: GifEntity)

    @Query("SELECT * FROM gif_table WHERE exerciseId = :id LIMIT 1")
    suspend fun getGifById(id: kotlin.String): GifEntity?
}