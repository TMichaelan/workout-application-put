package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_applications_project_put.activities.ExerciseDetailsActivity
import com.example.mobile_applications_project_put.activities.ExerciseListActivity
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.adapters.ExerciseAdapter
import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.functions.JsonUtility


class TestFragment : Fragment(), ExerciseAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onItemClick(exercise: Exercise) {
        val intent = Intent(requireContext(), ExerciseDetailsActivity::class.java)

        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)

        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)

        startActivity(intent)
    }

    companion object {
        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
    }

}
