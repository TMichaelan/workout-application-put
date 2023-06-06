package com.example.mobile_applications_project_put.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.databinding.BodyPartsBinding

class BodyPartAdapter: RecyclerView.Adapter<BodyPartAdapter.BodyPartsViewHolder>() {
    private var bodyPartsList = ArrayList<String>()


    fun setBodyPart(bodyPartsList:ArrayList<String>){
        this.bodyPartsList = bodyPartsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyPartsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BodyPartsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class BodyPartsViewHolder(val binding: BodyPartsBinding): RecyclerView.ViewHolder(binding.root)
}