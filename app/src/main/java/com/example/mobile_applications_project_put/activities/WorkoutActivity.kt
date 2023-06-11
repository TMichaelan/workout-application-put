package com.example.mobile_applications_project_put.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.ExerciseAdapter
import com.example.mobile_applications_project_put.adapters.ExerciseListAdapter
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem
import com.example.mobile_applications_project_put.models.MuscleGroup
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class WorkoutActivity: AppCompatActivity(), ExerciseAdapter.OnItemClickListener {
    private lateinit var exerciseList: List<Exercise>
    private lateinit var adapter: ExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_exercise_list)


        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        // Assign an empty list to the exerciseList
        exerciseList = emptyList()

//        adapter = ExerciseListAdapter(exerciseList, this)
//        recyclerView.adapter = adapter
//
//        lifecycleScope.launch {
//            exerciseList = ApiUtility.getBodyPartExercises(exerciseName?.muscleGroup?.lowercase() ?: "")
//            adapter.setExerciseList(exerciseList)
//        }


    }




    override fun onItemClick(exercise: Exercise) {
        val intent = Intent(this, ExerciseDetailsActivity::class.java)

        intent.putExtra("callingActivity", "ExerciseListActivity")

        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)
        intent.putExtra(ID, exercise.id)
        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)


       startActivity(intent)
    }

    companion object{
        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
        const val ID = "com.example.mobile_applications_project_put.fragments.id"
        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
    }
}