package com.example.mobile_applications_project_put.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
import com.example.mobile_applications_project_put.models.BodyPartExcerciseList
import com.example.mobile_applications_project_put.models.BodyPartsList
import com.example.mobile_applications_project_put.models.ExerciseItem
import com.example.mobile_applications_project_put.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bodyPartAdapter: BodyPartAdapter


    fun getBodyParts(){

        RetrofitInstance.excerciseAPI.getBodyPartsList().enqueue(object :  Callback<BodyPartsList>{

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val bodyParts = response.body()
                Log.d("TEST", "Body part:341341341341341341341341341")
                Log.d("TEST", "Body part:341341341341341341341341341")
                Log.d("TEST", "Body part:${bodyParts}")
                if (bodyParts != null) {
                    // Вывод данных
                    for (part in bodyParts) {
                        Log.d("TEST", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })

    }

    fun getTargetList(){

        RetrofitInstance.excerciseAPI.getTargetPartsList().enqueue(object :  Callback<BodyPartsList>{

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val targetParts = response.body()
                Log.d("TEST", "Body part:341341341341341341341341341")
                Log.d("TEST", "Body part:341341341341341341341341341")
                Log.d("TEST", "Body part:${targetParts}")
                if (targetParts != null) {
                    // Вывод данных
                    for (part in targetParts) {
                        Log.d("TEST", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })

    }

    fun getAllExercises() {
        RetrofitInstance.excerciseAPI.getAllExercisesList().enqueue(object :  Callback<BodyPartsList>{
            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val exercisesList = response.body()
                Log.d("TEST", "Response received for all exercises: $exercisesList")

                if (exercisesList != null) {
                    // Вывод данных
                    for (exercise in exercisesList) {
                        Log.d("TEST", "Exercise: $exercise")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })
    }

    fun getBodyPartExcersices(bodyPart : String) {
        RetrofitInstance.excerciseAPI.getBodyPart(bodyPart).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val bodyParts = response.body()
                Log.d("TEST", "Response received for body part: $bodyPart")
                if (bodyParts != null) {
                    // Print data
                    for (part in bodyParts) {
                        Log.d("TEST", "Body part: $part")
                    }
                } else {
                    Log.d("TEST", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })
    }


    fun getExerciseListByName(exersiceName : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByName(exersiceName).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("TEST", "Response received for body part: $exersiceName")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("TEST", "Body part: $exercise")
                    }
                } else {
                    Log.d("TEST", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseById(exersiceId : String) {
        RetrofitInstance.excerciseAPI.getExerciseIdById(exersiceId).enqueue(object :  Callback<ExerciseItem>{

            override fun onResponse(call: Call<ExerciseItem>, response: Response<ExerciseItem>) {
                val exerciseItem = response.body()
                Log.d("TEST", "Response received for exercise ID: $exersiceId")
                if (exerciseItem != null) {
                    // Print data
                    Log.d("TEST", "Exercise item: $exerciseItem")

                } else {
                    Log.d("TEST", "No exercise item returned in the response")
                }
            }

            override fun onFailure(call: Call<ExerciseItem>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseListByTarget(target : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByTarget(target).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("TEST", "Response received for body part: $target")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("TEST", "Body part: $exercise")
                    }
                } else {
                    Log.d("TEST", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("TEST", "Error: ${t.message}")
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun preparePopularItemRecycleView() {
        binding.recViewBodyParts.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getBodyParts()
//        getBodyPartExcersices("back")
//        getExerciseById("0007")
//        getExerciseListByName("alternate lateral pulldown")
//        getTargetList()
//        getExerciseListByTarget("abs")
//        getAllExercises()

        preparePopularItemRecycleView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}