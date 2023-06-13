package com.example.mobile_applications_project_put.db

import androidx.room.*
import com.example.mobile_applications_project_put.db.entities.GifEntity

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGif(gif: GifEntity)

    @Query("DELETE FROM gif_table WHERE exerciseId = :id")
    suspend fun deleteGifById(id: String)
    @Query("SELECT * FROM gif_table WHERE exerciseId = :id LIMIT 1")
    suspend fun getGifById(id: kotlin.String): GifEntity?
}