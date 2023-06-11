package com.example.mobile_applications_project_put.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class AddToWorkoutExercisesListActivity  : AppCompatActivity(), ExerciseAdapter.OnItemClickListener {
    private lateinit var exerciseList: List<Exercise>
    private lateinit var searchView: SearchView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_body_parts_list)


        val exerciseName = intent.getParcelableExtra<MuscleGroup>("muscle")

        searchView = findViewById(R.id.searchView)
        searchView.queryHint = "Exercise"

        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        // Assign an empty list to the exerciseList
        exerciseList = emptyList()

        adapter = ExerciseAdapter(exerciseList, this)
        recyclerView.adapter = adapter


        //TODO Добавить поиск всех упражнений
        lifecycleScope.launch {
//            exerciseList = ApiUtility.getAllExercises()
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
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Exercise>()
            val lowercaseQuery = query.lowercase(Locale.ROOT)
            for (i in exerciseList) {
                if (i.name.lowercase(Locale.ROOT).contains(lowercaseQuery)) {
                    filteredList.add(i)
                }
            }
            adapter.setExerciseList(filteredList)
        }
    }


    override fun onItemClick(exercise: Exercise) {
//        val exerciseItem = ExerciseItem(bodyPartExcerciseListItem.bodyPart, bodyPartExcerciseListItem.equipment, bodyPartExcerciseListItem.gifUrl, bodyPartExcerciseListItem.id, bodyPartExcerciseListItem.name, bodyPartExcerciseListItem.target)
        val intent = Intent(this, ExerciseDetailsActivity::class.java)

        intent.putExtra("callingActivity", "AddToWorkoutExercisesListActivity")

        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)
        intent.putExtra(ID, exercise.id)
        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)


//        Log.d("test", bodyPartExcerciseListItem.bodyPart)
//        Log.d("test", bodyPartExcerciseListItem.equipment)
//        Log.d("test", bodyPartExcerciseListItem.gifUrl)
//        Log.d("test",  bodyPartExcerciseListItem.name)
//        Log.d("test", bodyPartExcerciseListItem.target)

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