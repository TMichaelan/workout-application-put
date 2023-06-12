package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase

class LocalWorkoutListAdapter (
    private var workoutList: List<Workout>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<LocalWorkoutListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.local_workout_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentWorkout = workoutList[position]
        holder.bind(currentWorkout)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentWorkout)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(workout: Workout)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_workoutName)
        private val description: TextView = itemView.findViewById(R.id.tv_workoutDetails)

        fun bind(workout: Workout) {
            name.text = workout.name?.capitalize()
            description.text = workout.name?.capitalize()
        }
    }

    fun setExerciseList(workout: List<Workout>) {
        workoutList = workout
        notifyDataSetChanged()
    }
}
