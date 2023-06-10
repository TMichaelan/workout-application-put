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
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.ExerciseListActivity
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.adapters.ExerciseListAdapter
import com.example.mobile_applications_project_put.adapters.WorkoutsAdapter
import com.example.mobile_applications_project_put.db.entities.*
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.functions.DbUtility
import com.example.mobile_applications_project_put.functions.FirebaseUtility
import com.example.mobile_applications_project_put.functions.FirebaseUtility.deleteWorkout
import com.example.mobile_applications_project_put.functions.FirebaseUtility.getUserWorkouts
//import com.example.mobile_applications_project_put.functions.FirebaseUtility.getWorkoutsForUser
import com.example.mobile_applications_project_put.functions.JsonUtility
import com.example.mobile_applications_project_put.models.MuscleGroup
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List


//class WorkoutsFragment : Fragment(), WorkoutsAdapter.OnItemClickListener, WorkoutsAdapter.OnDeleteClickListener {
class WorkoutsFragment : Fragment(), WorkoutsAdapter.OnItemClickListener{
    private var workoutList: List<WorkoutFirebase> = ArrayList()
    private lateinit var adapter: WorkoutsAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val loggedIn = sharedPref?.getBoolean("logged_in", false)
        val username = sharedPref?.getString("username", null)

        val btn_add = view.findViewById(R.id.button3) as Button
        //TODO change button function

        val exx = JsonUtility.getRandomExercises(requireContext(), 3)

        getUserWorkouts(username!!) { workouts, error ->
            if (workouts != null) {
                // Обработка полученных тренировок
                for (workout in workouts) {
                    Log.d("GG", workout.name)
                    workoutList += workout
                }

                adapter = WorkoutsAdapter(workoutList, this)

                val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_workouts)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
                recyclerView.adapter = adapter

            } else {
                if (error != null) {
                    Log.d("GG", error)
                }
            }
        }

//        //DELETING WORKOUT

//        FirebaseUtility.deleteWorkout(username!!,"-NXaIJros5nL4dGEvKdg") { success, message ->
//            if (success) {
//                // If the workout was successfully deleted, remove it from the list and update the adapter
//                workoutList = workoutList.filter { it.id != "0" }.toMutableList()
//                adapter.updateList(workoutList)
//
//            } else {
//                // If an error occurred, display the error message
//                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//            }
//        }


        btn_add.setOnClickListener {

            val workout = WorkoutFirebase("0", "wkout", exx)

            FirebaseUtility.addWorkout(username!!, workout) { success, message ->
                if (success) {
//                    workoutList += WorkoutFirebase(workout.id.toString(), workout.name)
                    adapter.updateList(workoutList)
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
            }
        }

//        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_workouts)
//        recyclerView.layoutManager = GridLayoutManager(context, 1)
//        // adapter instance is set to the
//        // recyclerview to inflate the items.
//        recyclerView.adapter = adapter


    }

//    override fun onDeleteClick(workout: WorkoutFirebase) {
//        // Check if username is not null
//        if (username != null) {
//            // Delete workout if username is not null
//            FirebaseUtility.deleteWorkout(username!!, workout) { success, message ->
//                if (success) {
//                    // If the workout was successfully deleted, remove it from the list and update the adapter
//                    workoutList = workoutList.filter { it.id != workout.id }.toMutableList()
//                    adapter.updateList(workoutList)
//                } else {
//                    // If an error occurred, display the error message
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//                }
//            }
//        } else {
//            Toast.makeText(requireContext(), "Username is null", Toast.LENGTH_LONG).show()
//        }
//    }



    override fun onItemClick(workout: WorkoutFirebase) {
        // TODO: add and change activity
        val intent = Intent(requireContext(), ExerciseListActivity::class.java)
//         convert WorkoutFirebase back to Workout object if needed
//        val convertedWorkout = Workout(workout.id.toInt(), workout.name)
//        intent.putExtra("muscle", convertedWorkout)
        startActivity(intent)
    }


    companion object {
        @JvmStatic
        fun newInstance() = WorkoutsFragment().apply {}
    }
}