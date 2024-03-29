package com.example.mobile_applications_project_put.fragments

import android.content.Intent
import android.os.Bundle
//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.activities.ExerciseDetailsActivity
import com.example.mobile_applications_project_put.adapters.SmallExerciseListAdapter
import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.functions.JsonUtility


class HomeFragment : Fragment(), SmallExerciseListAdapter.OnItemClickListener{
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val ex = JsonUtility.getRandomExercises(context)

//        for (i in ex) {
//            DbUtility.dbAddExerciseById(requireContext(), i.id)
//            Log.d("IDEXER", "${i.id}")
//        }
//
//        DbUtility.createWorkout(requireContext(),"Random exercises workout")
//        DbUtility.addExerciseToWorkout(requireContext(), 0, "0003")


        val adapter = SmallExerciseListAdapter(ex, this)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = GridLayoutManager(context, 4)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        val bodyPartsListFragment = BodyPartsListFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView1, bodyPartsListFragment)
            .commit()
        return binding.root
    }

    override fun onItemClick(exercise: Exercise) {
        val intent = Intent(requireContext(), ExerciseDetailsActivity::class.java)

        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)
        intent.putExtra(ID, exercise.id)
        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)

        startActivity(intent)
    }

    companion object {
        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
        const val ID = "com.example.mobile_applications_project_put.fragments.id"
        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
    }

}