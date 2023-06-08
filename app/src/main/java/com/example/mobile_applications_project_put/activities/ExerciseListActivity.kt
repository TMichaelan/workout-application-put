package com.example.mobile_applications_project_put.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.ExerciseListAdapter
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem
import com.example.mobile_applications_project_put.models.MuscleGroup
import java.util.*
import kotlin.collections.ArrayList

class ExerciseListActivity : AppCompatActivity(), ExerciseListAdapter.OnItemClickListener {
    private val exerciseList = ArrayList<BodyPartExcerciseListItem>()
    private lateinit var searchView: SearchView
    private lateinit var adapter: ExerciseListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excersice_list)
        val exerciseName = intent.getParcelableExtra<MuscleGroup>("muscle")
        if (exerciseName != null) {
            ApiUtility.getBodyPartExcersices(exerciseName.muscleGroup)
            Log.d("ExerciseLog", "exerciseName ${exerciseName.muscleGroup}")

        } else {
            Toast.makeText(this, "Error: Exercises not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        searchView = findViewById(R.id.searchView)


        // Assign mealList to ItemAdapter
        adapter = ExerciseListAdapter(exerciseList, this)

        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = adapter
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

            if (!filteredList.isEmpty()) {
                adapter.setFilteredList(filteredList)
            }
            else{
                filteredList.clear()
                adapter.setFilteredList(filteredList)
            }
        }
    }


    override fun onItemClick(bodyPartExcerciseListItem: BodyPartExcerciseListItem) {
        val intent = Intent(this, ExerciseListActivity::class.java)
        intent.putExtra("bodyPartExcerciseListItem", bodyPartExcerciseListItem)
        startActivity(intent)
    }

}