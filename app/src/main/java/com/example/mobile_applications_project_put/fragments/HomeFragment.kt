package com.example.mobile_applications_project_put.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
import com.example.mobile_applications_project_put.models.BodyPartsList
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getdatatest(){

        RetrofitInstance.excerciseAPI.getBodyParts().enqueue(object :  Callback<BodyPartsList>{

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

    private fun preparePopularItemRecycleView() {
        binding.recViewBodyParts.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdatatest()

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