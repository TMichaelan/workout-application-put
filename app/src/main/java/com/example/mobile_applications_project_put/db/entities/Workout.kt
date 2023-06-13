package com.example.mobile_applications_project_put.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "workouts")
@Parcelize
data class Workout(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") val name: String
): Parcelable {
    constructor() : this(0, "")
}