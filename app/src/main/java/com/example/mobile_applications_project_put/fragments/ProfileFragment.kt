package com.example.mobile_applications_project_put.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobile_applications_project_put.activities.MapsActivity
import com.example.mobile_applications_project_put.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)

        binding.usernameText.text = username

        binding.calculateButton.setOnClickListener {
            calculateBMI()
        }

        binding.mapButton.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun calculateBMI() {
        val weight = binding.weightEdittext.text.toString().toFloatOrNull()
        val height = binding.heightEdittext.text.toString().toFloatOrNull()

        if (weight != null && height != null) {
            Log.d("test", weight.toString())
            Log.d("test", height.toString())

            val bmi = weight / (height / 100 * height / 100)

            binding.bmiResultText.text = "Your BMI: %.2f kg/m2".format(bmi)

            val bmiCategory = when {
                bmi < 16 -> "Severely underweight"
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal weight"
                bmi < 30 -> "Overweight"
                bmi < 35 -> "Obese Class I"
                bmi < 40 -> "Obese Class II"
                else -> "Obese Class III"
            }

            binding.bmiCategoryText.text = "BMI Category: $bmiCategory"
        } else {
            binding.bmiResultText.text = "Invalid input"
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment().apply { }
    }
}