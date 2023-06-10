package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.fragments.HomeFragment
import com.example.mobile_applications_project_put.fragments.WorkoutsFragment
import com.example.mobile_applications_project_put.models.MuscleGroup

class WorkoutsAdapter (
    private var workoutList: List<Workout>,
    private val listener: WorkoutsFragment
    ): RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
        return MyViewHolder(itemView, listener, workoutList)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentWorkout = workoutList[position]

        holder.name.text = currentWorkout.name.capitalize()
        holder.description.text = currentWorkout.name.capitalize()
    }


    interface OnItemClickListener {
        fun onItemClick(workout: Workout)
    }


    class MyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener,
        private val workoutList: List<Workout>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.tv_workoutName)
        val description: TextView = itemView.findViewById(R.id.tv_workoutDetails)
        private val deleteButton: Button = itemView.findViewById(R.id.button2)


        init {
            itemView.setOnClickListener(this)
            deleteButton.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedWorkout = workoutList[position]
                when (v?.id) {
                    R.id.button2 -> {
                    }
                    else -> {
                        // Handle item click
                        listener.onItemClick(clickedWorkout)
                    }
                }
            }
        }
    }

    fun updateList(list: List<Workout>){
        workoutList=list
        notifyDataSetChanged()
    }


}