package com.example.mobile_applications_project_put.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.adapters.WorkoutExerciseListAdapter
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.functions.FirebaseUtility
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class WorkoutActivity: AppCompatActivity(), WorkoutExerciseListAdapter.OnItemClickListener, WorkoutExerciseListAdapter.OnDeleteClickListener {
    private var exerciseList: List<Exercise> = ArrayList()
    private lateinit var adapter: WorkoutExerciseListAdapter
    private lateinit var editText: EditText
    private lateinit var username:String
    private lateinit var workoutId: String
    private lateinit var workoutName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_exercise_list)

        username = intent.getStringExtra("username").toString()
        workoutId = intent.getStringExtra("workoutId").toString()
        workoutName = intent.getStringExtra("workoutName").toString()
        Log.d("UserWorkout", "username: $username, workout: $workoutId")

        editText = findViewById(R.id.editText)
        // Получение текста из поля EditText
        editText.setText(workoutName)
        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Действия перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Действия при изменении текста
            }

            override fun afterTextChanged(s: Editable?) {
                // Действия после изменения текста]
                FirebaseUtility.updateWorkoutName(username, workoutId, s.toString()){success, message -> }
            }
        })

        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)

        adapter = WorkoutExerciseListAdapter(exerciseList, this, this)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = adapter


        lifecycleScope.launch {
            FirebaseUtility.getUserWorkoutExercises(username, workoutId) { exercises, errorMessage ->
                if (exercises != null) {
                    exerciseList = exercises.toMutableList()
                    adapter.setExerciseList(exerciseList)

                }
            }
        }




        val btn_add = findViewById<Button>(R.id.button_add_exercise)
        btn_add.setOnClickListener {
            val intent = Intent(this, AddToWorkoutBodyPartListActivity::class.java).apply {
                putExtra("username", username)
                putExtra("workoutId", workoutId)
                putExtra("workoutName", workoutName)
            }
            startActivityForResult(intent, REQUEST_CODE_ADD_EXERCISE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("Request_code", "hera")
        if (requestCode == REQUEST_CODE_ADD_EXERCISE && resultCode == RESULT_OK) {
            // Обработайте результат возврата здесь
            // Например, обновите список упражнений
            Log.d("Request_code", "hera")
            lifecycleScope.launch {
                delay(500)
                FirebaseUtility.getUserWorkoutExercises(username, workoutId) { exercises, errorMessage ->
                    if (exercises != null) {
                        exerciseList = exercises.toMutableList()
                        adapter.setExerciseList(exerciseList)
                    }
                    Log.d("Request_code", "$exerciseList")
                }
            }
        }
    }

    override fun onDeleteClick(exercise: Exercise) {
        exercise.id.let {
            FirebaseUtility.removeExerciseFromWorkout(username, workoutId, it) { success, message ->
                if (success) {
                    exerciseList = exerciseList.filter { it.id != exercise.id }
                    adapter.setExerciseList(exerciseList)
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onItemClick(exercise: Exercise) {
        val intent = Intent(this, ExerciseDetailsActivity::class.java)

        intent.putExtra("callingActivity", "ExerciseListActivity")
        intent.putExtra(BODYPART, exercise.bodyPart)
        intent.putExtra(EQUIPMENT, exercise.equipment)
        intent.putExtra(GIFURL, exercise.gifUrl)
        intent.putExtra(ID, exercise.id)
        intent.putExtra(NAME, exercise.name)
        intent.putExtra(TARGET, exercise.target)

        startActivity(intent)

    }

    companion object{
        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
        const val ID = "com.example.mobile_applications_project_put.fragments.id"
        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
        const val REQUEST_CODE_ADD_EXERCISE = 1
    }
}