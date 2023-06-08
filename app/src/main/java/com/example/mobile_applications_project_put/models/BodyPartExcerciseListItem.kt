package com.example.mobile_applications_project_put.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BodyPartExcerciseListItem(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: String,
    val name: String,
    val target: String
): Parcelable