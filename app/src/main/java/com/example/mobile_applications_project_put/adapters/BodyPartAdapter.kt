package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.Muscle
import com.example.mobile_applications_project_put.fragments.BodyPartsListFragment
import com.example.mobile_applications_project_put.models.BodyPartsList
import com.example.mobile_applications_project_put.models.MuscleGroup

class BodyPartAdapter(
    private var bodyPartsList: ArrayList<MuscleGroup>,
    private val listener: BodyPartsListFragment
    ): RecyclerView.Adapter<BodyPartAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.body_parts, parent, false)
        return MyViewHolder(itemView, listener, bodyPartsList)
    }
    override fun getItemCount(): Int {
        return bodyPartsList.size
    }

    private fun getResId(resName: String, c: Class<*>): Int {
        return try {
            val idField = c.getDeclaredField(resName)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMuscle = bodyPartsList[position]


        val resName = currentMuscle.image.substringAfterLast('.') // Extract the resource name from the string
        val imageResId = getResId(resName, R.drawable::class.java) // Get the resource ID
        if (imageResId != -1) { // If the resource exists
            Glide.with(holder.imageId)
                .load(imageResId)
                .into(holder.imageId)
        }
        holder.name.text = currentMuscle.muscleGroup
        holder.itemView.setOnClickListener { listener.onItemClick(currentMuscle) }
    }


    interface OnItemClickListener {
        fun onItemClick(muscleGroup: MuscleGroup)
    }


    class MyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener,
        private val bodyPartsList: ArrayList<MuscleGroup>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageId: ImageView = itemView.findViewById(R.id.img_body_part)
        val name: TextView = itemView.findViewById(R.id.tv_body_part)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(bodyPartsList[adapterPosition])
        }
    }

    fun setFilteredList(list: ArrayList<MuscleGroup>){
        bodyPartsList=list
        notifyDataSetChanged()
    }

}