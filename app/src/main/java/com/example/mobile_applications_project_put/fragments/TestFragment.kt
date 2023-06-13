//package com.example.mobile_applications_project_put.fragments
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.mobile_applications_project_put.activities.ExerciseDetailsActivity
//import com.example.mobile_applications_project_put.adapters.ExerciseAdapter
//import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
//import com.example.mobile_applications_project_put.db.entities.Exercise
//import com.example.mobile_applications_project_put.functions.FirebaseUtility
//import com.example.mobile_applications_project_put.functions.UserUtility
//
//
//class TestFragment : Fragment(), ExerciseAdapter.OnItemClickListener {
//    private lateinit var binding: FragmentHomeBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val context = requireContext()
//
//        val sharedPref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        val username = sharedPref?.getString("username", null)
//
//        val height = null
//        val weight = null
//
//        FirebaseUtility.updateUserWeight(username!!, 80.0) { success, message ->
//            if (success) {
//                Log.d("UpdateWeight", "Weight updated successfully")
//            } else {
//                Log.d("UpdateWeight", "Failed to update weight: $message")
//            }
//        }
//
//        FirebaseUtility.updateUserAge(username!!, 20) { success, message ->
//            if (success) {
//                Log.d("UpdateAge", "Age updated successfully")
//            } else {
//                Log.d("UpdateAge", "Failed to update age: $message")
//            }
//        }
//
//        FirebaseUtility.updateUserHeight(username!!, 180) { success, message ->
//            if (success) {
//                Log.d("UpdateHeight", "Height updated successfully")
//            } else {
//                Log.d("UpdateHeight", "Failed to update height: $message")
//            }
//        }
//
//        FirebaseUtility.updateUserName(username!!, "Jan Kowalski") { success, message ->
//            if (success) {
//                Log.d("UpdateName", "Name updated successfully")
//            } else {
//                Log.d("UpdateName", "Failed to update name: $message")
//            }
//        }
//
//        FirebaseUtility.getUser(username!!) { user, errorMessage ->
//            if (user != null) {
//                // We got the user, calculate the BMI
//                val bmi = user.weight?.let { UserUtility.calculateBMI(it, user.height) }
//                Log.d("BMI", "The calculated BMI is: $bmi")
//            } else {
//                // There was an error getting the user
//                Log.e("GetUser", "Failed to get user: $errorMessage")
//            }
//        }
//
//
//
//
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentHomeBinding.inflate(inflater,container, false)
//        return binding.root
//    }
//
//    override fun onItemClick(exercise: Exercise) {
//        val intent = Intent(requireContext(), ExerciseDetailsActivity::class.java)
//
//        intent.putExtra(BODYPART, exercise.bodyPart)
//        intent.putExtra(EQUIPMENT, exercise.equipment)
//        intent.putExtra(GIFURL, exercise.gifUrl)
//
//        intent.putExtra(NAME, exercise.name)
//        intent.putExtra(TARGET, exercise.target)
//
//        startActivity(intent)
//    }
//
//    companion object {
//        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
//        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
//        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
//        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
//        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
//    }
//
//}
