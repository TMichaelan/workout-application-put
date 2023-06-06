package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.databinding.BodyPartsBinding

class BodyPartAdapter: RecyclerView.Adapter<BodyPartAdapter.BodyPartsViewHolder>() {
    private var bodyPartsList = ArrayList<String>()


    fun setBodyPart(bodyPartsList:ArrayList<String>){
        this.bodyPartsList = bodyPartsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyPartsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.body_parts, parent, false)
        return MyViewHolder(itemView, listener, meallist)
    }

    override fun onBindViewHolder(holder: BodyPartsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    interface OnItemClickListener {
        fun onItemClick(muscle: Class)
    }

    class MyViewHolder(
        itemView: View,
        private val listener: AdapterView.OnItemClickListener,
        private val bodyPartsList: ArrayList<String>
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
    class BodyPartsViewHolder(val binding: BodyPartsBinding): RecyclerView.ViewHolder(binding.root)
}