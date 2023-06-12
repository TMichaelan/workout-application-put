package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.AddToWorkoutExerciseListActivity
import com.example.mobile_applications_project_put.models.BodyPartExcerciseListItem

class AddToWorkoutExerciseAdapter(
    private var exerciseList: List<BodyPartExcerciseListItem>,
    private val exerciseSelectedListener: OnExerciseSelectedListener

): RecyclerView.Adapter<AddToWorkoutExerciseAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_exercise_item, parent, false)
        return MyViewHolder(itemView, exerciseList, exerciseSelectedListener)
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
        holder.checkBox.isChecked = currentExercise.isSelected
    }

    interface OnExerciseSelectedListener {
        fun onExerciseSelected(exercise: BodyPartExcerciseListItem)
        fun onExerciseDeselected(exercise: BodyPartExcerciseListItem)
    }


    class MyViewHolder(
        itemView: View,
        private val exerciseList: List<BodyPartExcerciseListItem>,
        private val exerciseSelectedListener: OnExerciseSelectedListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageId: ImageView = itemView.findViewById(R.id.img_body_part)
        val name: TextView = itemView.findViewById(R.id.tv_body_part)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener(this)
            checkBox.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val exercise = exerciseList[position]
            exercise.isSelected = checkBox.isChecked
            if (checkBox.isChecked) {
                exerciseSelectedListener.onExerciseSelected(exercise)
            } else {
                exerciseSelectedListener.onExerciseDeselected(exercise)
            }
        }
    }

    fun setFilteredList(list: List<BodyPartExcerciseListItem>) {
        exerciseList = list
        notifyDataSetChanged()
    }

    fun setExerciseList(exercises: List<BodyPartExcerciseListItem>) {
        exerciseList = exercises
        notifyDataSetChanged()
    }
}