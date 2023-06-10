package com.example.mobile_applications_project_put.functions

import android.content.Context
import android.util.Log
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.models.MuscleGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
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

    fun getRandomExercises(context: Context): List<Exercise> {
        val inputStream: InputStream = context.assets.open("exercises.json")
        val json = inputStream.bufferedReader().use { it.readText() }

        val listType = object : TypeToken<List<Exercise>>() {}.type
        val exercises: List<Exercise> = Gson().fromJson(json, listType)

        return exercises.shuffled().take(20) // Перемешивание списка и взятие первых 20 элементов
    }


}