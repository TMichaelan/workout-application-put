package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.models.BodyPartsList


class BodyPartsListFragment : Fragment(), BodyPartAdapter.OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_parts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bodyPartsList = arguments?.getSerializable("bodyPartsList") as? ArrayList<BodyPartsList>

        // Assign mealList to ItemAdapter
        val itemAdapter = bodyPartsList?.let { BodyPartAdapter(it, this) }
        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter
    }

    override fun onItemClick(muscle: BodyPartsList) {
        val intent = Intent(requireContext(), BodyPartsListFragment::class.java)
        intent.putExtra("muscle", muscle)
        startActivity(intent)
    }


    companion object {

        @JvmStatic
        fun newInstance() = BodyPartsListFragment().apply {}
    }
}