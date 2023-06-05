package com.example.mobile_applications_project_put.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobile_applications_project_put.R
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdatatest()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
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