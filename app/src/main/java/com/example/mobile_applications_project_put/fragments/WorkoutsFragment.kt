package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.ExerciseListActivity
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.adapters.ExerciseListAdapter
import com.example.mobile_applications_project_put.adapters.WorkoutsAdapter
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.functions.ApiUtility
import com.example.mobile_applications_project_put.functions.DbUtility
import com.example.mobile_applications_project_put.functions.JsonUtility
import com.example.mobile_applications_project_put.models.MuscleGroup
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class WorkoutsFragment : Fragment(), WorkoutsAdapter.OnItemClickListener {
    private lateinit var workoutList: List<Workout>
    private lateinit var adapter: WorkoutsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutList = emptyList()

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



        workoutList = DbUtility.loadWorkouts(requireContext())


        adapter = WorkoutsAdapter(workoutList, this)

        val btn_add = view.findViewById(R.id.button3) as Button
        //TODO change button function
        btn_add.setOnClickListener {
            workoutList += Workout(1, "Name")
            adapter.updateList(workoutList)
        }




        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_workouts)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = adapter


    }


    override fun onItemClick(workout: Workout) {
        //TODO add and change activity
        val intent = Intent(requireContext(), ExerciseListActivity::class.java)
        intent.putExtra("muscle", workout)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutsFragment().apply {}
    }
}