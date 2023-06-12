package com.example.mobile_applications_project_put.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.AddToWorkoutExerciseAdapter
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.functions.FirebaseUtility
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem
import com.example.mobile_applications_project_put.models.MuscleGroup
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AddToWorkoutExerciseListActivity : AppCompatActivity(), AddToWorkoutExerciseAdapter.OnExerciseSelectedListener {
    private lateinit var exerciseList: List<BodyPartExcerciseListItem>
    private val selectedExerciseList: ArrayList<BodyPartExcerciseListItem> = ArrayList()
    private lateinit var searchView: SearchView
    private lateinit var adapter: AddToWorkoutExerciseAdapter
    private lateinit var username:String
    private lateinit var workoutId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_to_workout_exercise_list)

        username = intent.getStringExtra("username").toString()
        workoutId = intent.getStringExtra("workoutId").toString()
        val exerciseName = intent.getParcelableExtra<MuscleGroup>("muscle")

        searchView = findViewById(R.id.searchView)
        searchView.queryHint = "Exercise"

        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        // Assign an empty list to the exerciseList
        exerciseList = emptyList()

        adapter = AddToWorkoutExerciseAdapter(exerciseList, this)

        recyclerView.adapter = adapter

        lifecycleScope.launch {
            exerciseList = ApiUtility.getBodyPartExercises(exerciseName?.muscleGroup?.lowercase() ?: "")
            adapter.setExerciseList(exerciseList)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


        val btn_add = findViewById<Button>(R.id.add_selected)
        btn_add.setOnClickListener {
            for (bodyPartExcerciseListItem in selectedExerciseList) {
                val exercise = Exercise(
                    bodyPartExcerciseListItem.id,
                    bodyPartExcerciseListItem.bodyPart,
                    bodyPartExcerciseListItem.equipment,
                    bodyPartExcerciseListItem.gifUrl,
                    bodyPartExcerciseListItem.name,
                    bodyPartExcerciseListItem.target
                )

                Log.d("ThisItem", "$exercise")
                FirebaseUtility.addExerciseToWorkout(
                        username,
                        workoutId,
                        exercise
                    ) { success, message -> }
                }
            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("position", 1)
//            intent.putExtra("workoutId", workoutId)
            startActivity(intent)
            }


        }



    override fun onExerciseSelected(exercise: BodyPartExcerciseListItem) {
        if (!selectedExerciseList.contains(exercise)) {
            selectedExerciseList.add(exercise)
        }
    }

    override fun onExerciseDeselected(exercise: BodyPartExcerciseListItem) {
        selectedExerciseList.remove(exercise)
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<BodyPartExcerciseListItem>()
            val lowercaseQuery = query.lowercase(Locale.ROOT)
            for (i in exerciseList) {
                if (i.name.lowercase(Locale.ROOT).contains(lowercaseQuery)) {
                    filteredList.add(i)
                }
            }
            adapter.setFilteredList(filteredList)
        }
    }





    companion object{
    }
}