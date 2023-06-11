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
import com.example.mobile_applications_project_put.activities.ExerciseListActivity
import com.example.mobile_applications_project_put.activities.WorkoutActivity
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem

class WorkoutExerciseListAdapter(
    private var exerciseList: List<Exercise>,
    private val listener: WorkoutActivity,
    private val deleteListener: OnDeleteClickListener
): RecyclerView.Adapter<WorkoutExerciseListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.workout_exercise_item, parent, false)
        return MyViewHolder(itemView, listener, exerciseList)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentExercise = exerciseList[position]
        Glide.with(holder.imageId)
            .asBitmap()
            .load(currentExercise.gifUrl)
            .into(holder.imageId)
        holder.name.text = currentExercise.name.capitalize()
        holder.itemView.setOnClickListener { listener.onItemClick(currentExercise) }

        holder.deleteButton.setOnClickListener {
            deleteListener.onDeleteClick(currentExercise)
        }
    }


    interface OnItemClickListener {
        fun onItemClick(exercise: Exercise)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(exercise: Exercise)
    }

    class MyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener,
        private val exerciseList: List<Exercise>,
        val deleteButton: Button = itemView.findViewById(R.id.button2)
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageId: ImageView = itemView.findViewById(R.id.img_body_part)
        val name: TextView = itemView.findViewById(R.id.tv_body_part)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(exerciseList[adapterPosition])
        }
    }


    fun setExerciseList(exercises: List<Exercise>) {
        exerciseList = exercises
        notifyDataSetChanged()
    }



}