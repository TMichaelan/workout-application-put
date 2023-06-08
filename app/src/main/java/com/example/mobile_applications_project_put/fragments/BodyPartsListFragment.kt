package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.ExerciseListActivity
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.functions.JsonUtility
import com.example.mobile_applications_project_put.models.MuscleGroup
import java.util.*
import kotlin.collections.ArrayList


class BodyPartsListFragment : Fragment(), BodyPartAdapter.OnItemClickListener {
    private val bodyPartsList = ArrayList<MuscleGroup>()
    private val functions = JsonUtility
    private lateinit var searchView: SearchView
    private lateinit var adapter: BodyPartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        functions.readAndLogBodyParts(requireContext(), bodyPartsList)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_parts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = view.findViewById(R.id.searchView)


        // Assign mealList to ItemAdapter
        adapter = BodyPartAdapter(bodyPartsList, this)

        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_body_parts)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<MuscleGroup>()
            val lowercaseQuery = query.lowercase(Locale.ROOT)
            for (i in bodyPartsList) {
                if (i.muscleGroup.lowercase(Locale.ROOT).contains(lowercaseQuery)) {
                    filteredList.add(i)
                }
            }

            if (!filteredList.isEmpty()) {
                adapter.setFilteredList(filteredList)
            }
            else{
                filteredList.clear()
                adapter.setFilteredList(filteredList)
            }
        }
    }


    override fun onItemClick(muscleGroup: MuscleGroup) {
        val intent = Intent(requireContext(), ExerciseListActivity::class.java)
        intent.putExtra("muscle", muscleGroup)
        startActivity(intent)
    }




    companion object {

        @JvmStatic
        fun newInstance() = BodyPartsListFragment().apply {}
    }
}