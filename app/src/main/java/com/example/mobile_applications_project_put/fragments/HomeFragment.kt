package com.example.mobile_applications_project_put.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mobile_applications_project_put.adapters.BodyPartAdapter
import com.example.mobile_applications_project_put.databinding.FragmentHomeBinding
import com.example.mobile_applications_project_put.db.AppDatabase
import com.example.mobile_applications_project_put.db.ExersiceDao
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.models.BodyPartExcerciseList
import com.example.mobile_applications_project_put.models.BodyPartsList
import com.example.mobile_applications_project_put.models.ExerciseItem
import com.example.mobile_applications_project_put.models.MuscleGroup
import com.example.mobile_applications_project_put.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutExerciseCrossRef

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

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


    private var exerciseDao: ExersiceDao? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var database: AppDatabase
    private lateinit var exercise: Exercise

    fun getBodyParts(){

        RetrofitInstance.excerciseAPI.getBodyPartsList().enqueue(object :  Callback<BodyPartsList>{

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val bodyParts = response.body()
                Log.d("getBodyParts", "Body part:${bodyParts}")
                if (bodyParts != null) {
                    // Вывод данных
                    for (part in bodyParts) {
                        Log.d("getBodyParts", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getBodyParts", "Error: ${t.message}")
            }
        })

    }

    fun getTargetList(){

        RetrofitInstance.excerciseAPI.getTargetPartsList().enqueue(object :  Callback<BodyPartsList>{

            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val targetParts = response.body()

                Log.d("getTargetList", "Body part:${targetParts}")
                if (targetParts != null) {
                    // Вывод данных
                    for (part in targetParts) {
                        Log.d("getTargetList", "Body part: ${part}")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getTargetList", "Error: ${t.message}")
            }
        })

    }

    fun getAllExercises() {
        RetrofitInstance.excerciseAPI.getAllExercisesList().enqueue(object :  Callback<BodyPartsList>{
            override fun onResponse(call: Call<BodyPartsList>, response: Response<BodyPartsList>) {
                val exercisesList = response.body()
                Log.d("getAllExercises", "Response received for all exercises: $exercisesList")

                if (exercisesList != null) {
                    // Вывод данных
                    for (exercise in exercisesList) {
                        Log.d("getAllExercises", "Exercise: $exercise")
                    }
                }
            }

            override fun onFailure(call: Call<BodyPartsList>, t: Throwable) {
                Log.e("getAllExercises", "Error: ${t.message}")
            }
        })
    }

    fun getBodyPartExcersices(bodyPart : String) {
        RetrofitInstance.excerciseAPI.getBodyPart(bodyPart).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val bodyParts = response.body()
                Log.d("getBodyPartExcersices", "Response received for body part: $bodyPart")
                if (bodyParts != null) {
                    // Print data
                    for (part in bodyParts) {
                        Log.d("getBodyPartExcersices", "Body part: $part")
                    }
                } else {
                    Log.d("getBodyPartExcersices", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("getBodyPartExcersices", "Error: ${t.message}")
            }
        })
    }


    fun getExerciseListByName(exersiceName : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByName(exersiceName).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("getExerciseListByName", "Response received for body part: $exersiceName")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("getExerciseListByName", "Body part: $exercise")
                    }
                } else {
                    Log.d("getExerciseListByName", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("getExerciseListByName", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseById(exersiceId : String) {
        RetrofitInstance.excerciseAPI.getExerciseIdById(exersiceId).enqueue(object :  Callback<ExerciseItem>{

            override fun onResponse(call: Call<ExerciseItem>, response: Response<ExerciseItem>) {
                val exerciseItem = response.body()
                Log.d("getExerciseById", "Response received for exercise ID: $exersiceId")
                if (exerciseItem != null) {
                    // Print data
                    Log.d("getExerciseById", "Exercise item: $exerciseItem")

                } else {
                    Log.d("getExerciseById", "No exercise item returned in the response")
                }
            }

            override fun onFailure(call: Call<ExerciseItem>, t: Throwable) {
                Log.e("getExerciseById", "Error: ${t.message}")
            }
        })
    }

    fun getExerciseListByTarget(target : String) {
        RetrofitInstance.excerciseAPI.getExerciseListByTarget(target).enqueue(object :  Callback<BodyPartExcerciseList>{

            override fun onResponse(call: Call<BodyPartExcerciseList>, response: Response<BodyPartExcerciseList>) {
                val exercisesList = response.body()
                Log.d("getExerciseListByTarget", "Response received for body part: $target")
                if (exercisesList != null) {
                    // Print data
                    for (exercise in exercisesList) {
                        Log.d("getExerciseListByTarget", "Body part: $exercise")
                    }
                } else {
                    Log.d("getExerciseListByTarget", "No body parts returned in the response")
                }
            }

            override fun onFailure(call: Call<BodyPartExcerciseList>, t: Throwable) {
                Log.e("getExerciseListByTarget", "Error: ${t.message}")
            }
        })
    }

    fun readAndLogMuscleGroups(context: Context) {
        val assetManager = context.assets
        val inputStream = assetManager.open("target_list.json")
        val reader = InputStreamReader(inputStream)

        val gson = Gson()
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        val muscleGroups: List<MuscleGroup> = gson.fromJson(reader, listType)

        for (muscleGroup in muscleGroups) {
            Log.d("readAndLogMuscleGroups", "Muscle Group: ${muscleGroup.muscleGroup}, Description: ${muscleGroup.description}, Image: ${muscleGroup.image}")
        }
    }

    fun readAndLogBodyParts(context: Context) {
        val assetManager = context.assets
        val inputStream = assetManager.open("bodyparts_list.json")
        val reader = InputStreamReader(inputStream)

        val gson = Gson()
        val listType = object : TypeToken<List<MuscleGroup>>() {}.type
        val bodyParts: List<MuscleGroup> = gson.fromJson(reader, listType)

        for (part in bodyParts) {
            Log.d("readAndLogBodyParts", "Body part: ${part.muscleGroup}, Description: ${part.description}, Image: ${part.image}")
        }
    }

    fun deleteAppDatabase(context: Context) {
        context.deleteDatabase("exercise_db")
    }

    fun dbAddExerciseById(exersiceId: String) {
        RetrofitInstance.excerciseAPI.getExerciseIdById(exersiceId).enqueue(object :  Callback<ExerciseItem>{

            override fun onResponse(call: Call<ExerciseItem>, response: Response<ExerciseItem>) {
                val exerciseItem = response.body()
                Log.d("dbAddExerciseById", "Response received for exercise ID: $exersiceId")
                if (exerciseItem != null) {

                    Log.d("dbAddExerciseById", "Exercise item: $exerciseItem")

                    // Convert ExerciseItem to Exercise
                    val exercise = Exercise(
                        id = exerciseItem.id,
                        bodyPart = exerciseItem.bodyPart,
                        equipment = exerciseItem.equipment,
                        gifUrl = exerciseItem.gifUrl,
                        name = exerciseItem.name,
                        target = exerciseItem.target
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val exerciseInDb = database.exerciseDao().getExerciseById(exercise.id)
                        if (exerciseInDb != null) {
                            database.exerciseDao().deleteExercise(exerciseInDb)
                        } else {
                            database.exerciseDao().insert(exercise)
                        }
                    }

                } else {
                    Log.d("dbAddExerciseById", "No exercise item returned in the response")
                }
            }

            override fun onFailure(call: Call<ExerciseItem>, t: Throwable) {
                Log.e("dbAddExerciseById", "Error: ${t.message}")
            }
        })
    }

    private fun loadSavedExercises() {
        lifecycleScope.launch {
            val savedExercises = withContext(Dispatchers.IO) {
                database.exerciseDao().getAllExercises()
            }

            for (exercise in savedExercises) {
                Log.d("LOADSAVEDEXERCISES", "Saved exercise: $exercise")
            }

        }
    }


    fun createWorkout(name: String) {
        val newWorkout = Workout(name = name, id = 0)

        CoroutineScope(Dispatchers.IO).launch {
            val workoutId = AppDatabase.getInstance(requireContext()).workoutDao().insertWorkout(newWorkout)
            Log.d("Workout Creation", "Created workout with ID: $workoutId")
        }
    }
    fun addExerciseToWorkout(workoutId: Int, exerciseId: String) {
        val crossRef = WorkoutExerciseCrossRef(workoutId, exerciseId)

        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(requireContext()).workoutWithExercisesDao().insertWorkoutExerciseCrossRef(crossRef)
            Log.d("Workout Creation", "Added exercise with ID: $exerciseId to workout with ID: $workoutId")
        }
    }

    private fun loadWorkouts() {
        lifecycleScope.launch {
            val workouts = withContext(Dispatchers.IO) {
                database.workoutDao().getAllWorkouts()
            }

            for (workout in workouts) {
                Log.d("loadWorkouts", "Saved workouts: $workout")
            }

        }
    }

//    private fun loadWorkoutWithExercises(workoutId: Int) {
//        lifecycleScope.launch {
//            val workout = withContext(Dispatchers.IO) {
//                database.workoutWithExercisesDao().getWorkoutWithExercises(workoutId)
//            }
//            Log.d("loadWorkouts", "Saved workouts: $workout")
//
//        }
//    }

    private fun loadWorkoutWithExercises(workoutId: Int) {
        val workoutLiveData = database.workoutWithExercisesDao().getWorkoutWithExercises(workoutId)

        workoutLiveData.observe(viewLifecycleOwner, Observer { workout ->
            Log.d("loadWorkouts", "Saved workouts: $workout")
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getInstance(requireContext())

    }

//    private fun preparePopularItemRecycleView() {
//        binding.recViewBodyParts.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getBodyParts()
//        getBodyPartExcersices("back")
//        getExerciseById("0007")
//        getExerciseListByName("alternate lateral pulldown")
//        getTargetList()
//        getExerciseListByTarget("abs")
//        getAllExercises()
//        readAndLogMuscleGroups(requireContext())
//        readAndLogBodyParts(requireContext())
//        addExerciseByIdToDB("0003")

//        addExerciseByIdToDB("0011")
//        deleteAppDatabase(requireContext())

//        dbAddExerciseById("0001")

//        loadSavedExercises()

//        createWorkout("Test Workout")
//        createWorkout("DEN NOG")
//        createWorkout("Test Workout3")

//        addExerciseToWorkout(1, "0006")

//        loadWorkouts()

//        addExerciseToWorkout(4, "0003")
//        addExerciseToWorkout(4, "0007")

//        loadWorkoutWithExercises(4)

//        preparePopularItemRecycleView()
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