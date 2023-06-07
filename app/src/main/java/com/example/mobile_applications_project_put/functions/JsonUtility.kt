package com.example.mobile_applications_project_put.functions

import android.content.Context
import android.util.Log
import com.example.mobile_applications_project_put.models.MuscleGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object JsonUtility {

    fun readAndLogBodyParts(context: Context, bodyPartsList: ArrayList<MuscleGroup> = ArrayList()) {
        val assetManager = context.assets
        val inputStream = assetManager.open("bodyparts_list.json")
        val reader = InputStreamReader(inputStream)

        val gson = Gson()
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        val bodyParts: List<MuscleGroup> = gson.fromJson(reader, listType)

        for (part in bodyParts) {
            Log.d("BodyPartsData", "Body part: ${part.muscleGroup}, Description: ${part.description}, Image: ${part.image}")
            bodyPartsList.add(MuscleGroup(part.muscleGroup, part.description, part.image))
        }

    }
    fun readAndLogMuscleGroups(context: Context) {
        val assetManager = context.assets
        val inputStream = assetManager.open("target_list.json")
        val reader = InputStreamReader(inputStream)

        val gson = Gson()
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        val muscleGroups: List<MuscleGroup> = gson.fromJson(reader, listType)

        for (muscleGroup in muscleGroups) {
            Log.d("readAndLogMuscleGroups", "Muscle Group: ${muscleGroup.muscleGroup}, Description: ${muscleGroup.description}, Image: ${muscleGroup.image}")
        }
    }

}