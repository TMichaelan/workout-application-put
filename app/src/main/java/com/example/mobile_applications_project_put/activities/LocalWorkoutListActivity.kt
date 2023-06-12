package com.example.mobile_applications_project_put.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.LocalWorkoutListAdapter
import com.example.mobile_applications_project_put.databinding.ActivityLocalWorkoutsBinding
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase

class LocalWorkoutListActivity : AppCompatActivity(), LocalWorkoutListAdapter.OnItemClickListener{
    lateinit var binding: ActivityLocalWorkoutsBinding
    private lateinit var adapter: LocalWorkoutListAdapter
    private var workoutList: MutableList<WorkoutFirebase> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO добавить workoutList

        adapter = LocalWorkoutListAdapter(workoutList, this)

        val recyclerView: RecyclerView = findViewById(R.id.rec_view_workouts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = adapter

    }
    override fun onItemClick(workout: WorkoutFirebase) {
        val intent = Intent(this, WorkoutActivity::class.java)
        intent.putExtra("workoutId", workout.id)
        intent.putExtra("workoutName", workout.name)
        startActivity(intent)
    }


}