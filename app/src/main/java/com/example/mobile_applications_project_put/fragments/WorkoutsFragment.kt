package com.example.mobile_applications_project_put.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.WorkoutActivity
import com.example.mobile_applications_project_put.adapters.WorkoutsAdapter
import com.example.mobile_applications_project_put.db.entities.*
import com.example.mobile_applications_project_put.functions.FirebaseUtility
import com.example.mobile_applications_project_put.functions.FirebaseUtility.getUserWorkouts
import com.example.mobile_applications_project_put.functions.JsonUtility
import kotlin.collections.ArrayList

class WorkoutsFragment : Fragment(), WorkoutsAdapter.OnItemClickListener, WorkoutsAdapter.OnDeleteClickListener {
    private lateinit var adapter: WorkoutsAdapter
    private var username: String? = null
    private lateinit var progressBar: ProgressBar
    private var workoutList: MutableList<WorkoutFirebaseList> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        username = sharedPref?.getString("username", null)

        progressBar = view.findViewById(R.id.add_workout_progress_bar)
        val btn_add = view.findViewById(R.id.button3) as Button



        showProgressBar()
        //Updates the list of workouts
        getUserWorkouts(username!!) { workouts, error ->
            if (workouts != null) {

                workoutList = workouts.toMutableList()
                adapter = WorkoutsAdapter(workoutList, this, this)

                val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_workouts)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                recyclerView.adapter = adapter
                hideProgressBar()
            } else {
                if (error != null) {
                    Log.d("GG", error)
                }
            }
        }

        // Get random exercises from JSON file + make a workout
//        val exx = JsonUtility.getRandomExercises(requireContext(), 3)
        btn_add.setOnClickListener {
            val workout = WorkoutFirebaseList(name="My new workout")

            FirebaseUtility.addWorkout(username!!, workout) { success, message ->
                if (success) {
                    workoutList += workout
                    adapter.updateList(workoutList)
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        //Updates the list of workouts
        getUserWorkouts(username!!) { workouts, error ->
            if (workouts != null) {

                workoutList = workouts.toMutableList()
                adapter = WorkoutsAdapter(workoutList, this, this)

                val recyclerView: RecyclerView = requireView().findViewById(R.id.rec_view_workouts)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                recyclerView.adapter = adapter
            } else {
                if (error != null) {
                    Log.d("GG", error)
                }
            }
        }
        Log.d("resumehere", "wor")
    }
    override fun onDeleteClick(workout: WorkoutFirebaseList) {
        val sharedPref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        username = sharedPref?.getString("username", null)

        if (username != null) {
            workout.id?.let {
                FirebaseUtility.deleteWorkout(username!!, it) { success, message ->
                    if (success) {
                        workoutList = workoutList.filter { it.id != workout.id }.toMutableList()
                        adapter.updateList(workoutList)
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Username is null", Toast.LENGTH_LONG).show()
        }
    }


    override fun onItemClick(workout: WorkoutFirebaseList) {
        val intent = Intent(requireContext(), WorkoutActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("workoutId", workout.id)
        intent.putExtra("workoutName", workout.name)
        startActivity(intent)
    }
    
    companion object {
        @JvmStatic
        fun newInstance() = WorkoutsFragment().apply {}
    }
}