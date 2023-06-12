package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebaseList

class WorkoutsAdapter(
    private var workoutList: List<WorkoutFirebaseList>,
    private val listener: OnItemClickListener,
    private val deleteListener: OnDeleteClickListener
) : RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
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

        holder.deleteButton.setOnClickListener {
            deleteListener.onDeleteClick(currentWorkout)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(workout: WorkoutFirebaseList)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(workout: WorkoutFirebaseList)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_workoutName)
        private val description: TextView = itemView.findViewById(R.id.tv_workoutDetails)
        val deleteButton: Button = itemView.findViewById(R.id.button2)

        fun bind(workout: WorkoutFirebaseList) {
            name.text = workout.name?.capitalize()
            description.text = workout.name?.capitalize()
        }
    }


    fun updateList(list: List<WorkoutFirebaseList>) {
        workoutList = list
        notifyDataSetChanged()
    }
}
