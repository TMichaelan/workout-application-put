package com.example.mobile_applications_project_put.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.ExerciseListAdapter
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem
import com.example.mobile_applications_project_put.models.MuscleGroup
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
class ExerciseListActivity : AppCompatActivity(), ExerciseListAdapter.OnItemClickListener {
    private lateinit var exerciseList: List<BodyPartExcerciseListItem>
    private lateinit var searchView: SearchView
    private lateinit var adapter: ExerciseListAdapter

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

        adapter = ExerciseListAdapter(exerciseList, this)
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


    override fun onItemClick(bodyPartExcerciseListItem: BodyPartExcerciseListItem) {
        val intent = Intent(this, ExerciseDetailsActivity::class.java)

        intent.putExtra("callingActivity", "ExerciseListActivity")

        intent.putExtra(BODYPART, bodyPartExcerciseListItem.bodyPart)
        intent.putExtra(EQUIPMENT, bodyPartExcerciseListItem.equipment)
        intent.putExtra(GIFURL, bodyPartExcerciseListItem.gifUrl)
        intent.putExtra(ID, bodyPartExcerciseListItem.id)
        intent.putExtra(NAME, bodyPartExcerciseListItem.name)
        intent.putExtra(TARGET, bodyPartExcerciseListItem.target)

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