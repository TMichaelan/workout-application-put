package com.example.mobile_applications_project_put.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.LocalWorkoutListAdapter
import com.example.mobile_applications_project_put.databinding.ActivityLocalWorkoutsBinding
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase
import com.example.mobile_applications_project_put.functions.DbUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalWorkoutListActivity : AppCompatActivity(), LocalWorkoutListAdapter.OnItemClickListener {
    lateinit var binding: ActivityLocalWorkoutsBinding
    private lateinit var adapter: LocalWorkoutListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.rec_view_workouts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        adapter = LocalWorkoutListAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val workouts = DbUtility.loadWorkouts(this@LocalWorkoutListActivity)
            Log.d("workoutList", "$workouts")
            adapter.setExerciseList(workouts)
        }

    }

    override fun onItemClick(workout: Workout) {
        val intent = Intent(this, LocalWorkoutActivity::class.java)
        intent.putExtra("workoutId", workout.id)
        intent.putExtra("workoutName", workout.name)
        startActivity(intent)
    }

}