package com.example.mobile_applications_project_put.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "gif_table",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GifEntity(
    @PrimaryKey val exerciseId: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val gif: ByteArray
)