package com.example.mobile_applications_project_put.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MuscleGroup(
    val muscleGroup: String,
    val description: String,
    val image: String
): Parcelable