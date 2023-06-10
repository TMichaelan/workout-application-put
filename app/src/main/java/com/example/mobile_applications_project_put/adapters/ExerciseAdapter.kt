package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.models.ExerciseItem


class ExerciseAdapter(private val exerciseList: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]

        holder.tvName.text = currentItem.name

        Glide.with(holder.itemView.context)
            .asGif() // Added this line to handle gif files.
            .load(currentItem.gifUrl)
            .into(holder.imageView)
    }

    override fun getItemCount() = exerciseList.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
    }
}

