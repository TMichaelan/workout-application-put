package com.example.mobile_applications_project_put.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.fragments.BodyPartsListFragment
import com.example.mobile_applications_project_put.models.BodyPartsList

class BodyPartAdapter(
    private var bodyPartsList: ArrayList<BodyPartsList>,
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMuscle = bodyPartsList[position]
//        Glide.with(holder.imageId)
//            .load(currentMuscle.gifUrl)
//            .into(holder.imageId)
//        holder.name.text = currentMuscle.name
        holder.itemView.setOnClickListener { listener.onItemClick(currentMuscle) }
    }
    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<BodyPartsList>) {
        // below line is to add our filtered
        // list in our course array list.
        bodyPartsList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }
    interface OnItemClickListener {
        fun onItemClick(muscle: BodyPartsList)
    }


    class MyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener,
        private val bodyPartsList: ArrayList<BodyPartsList>
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
}