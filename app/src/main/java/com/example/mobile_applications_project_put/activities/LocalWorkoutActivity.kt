//package com.example.mobile_applications_project_put.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.mobile_applications_project_put.R
//import com.example.mobile_applications_project_put.adapters.LocalWorkoutExerciseListAdapter
//import com.example.mobile_applications_project_put.db.entities.Exercise
//
//class LocalWorkoutActivity : AppCompatActivity(), LocalWorkoutExerciseListAdapter.OnItemClickListener {
//    private var exerciseList: List<Exercise> = ArrayList()
//    private lateinit var adapter: LocalWorkoutExerciseListAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_local_workout)
//
//        val text = findViewById<TextView>(R.id.tv_workoutName)
//        text.setText(intent.getStringExtra("workoutName").toString())
//
//        val recyclerView: RecyclerView = findViewById(R.id.rec_view_body_parts)
//
//        adapter = LocalWorkoutExerciseListAdapter(exerciseList, this)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//    }
//
//    override fun onItemClick(exercise: Exercise) {
//        val intent = Intent(this, ExerciseDetailsActivity::class.java)
//
//        intent.putExtra("callingActivity", "LocalWorkoutActivity")
//        intent.putExtra(BODYPART, exercise.bodyPart)
//        intent.putExtra(EQUIPMENT, exercise.equipment)
//        intent.putExtra(GIFURL, exercise.gifUrl)
//        intent.putExtra(ID, exercise.id)
//        intent.putExtra(NAME, exercise.name)
//        intent.putExtra(TARGET, exercise.target)
//
//        startActivity(intent)
//    }
//    companion object{
//        const val BODYPART = "com.example.mobile_applications_project_put.fragments.bodyPart"
//        const val EQUIPMENT = "com.example.mobile_applications_project_put.fragments.equipment"
//        const val GIFURL = "com.example.mobile_applications_project_put.fragments.gifUrl"
//        const val ID = "com.example.mobile_applications_project_put.fragments.id"
//        const val NAME = "com.example.mobile_applications_project_put.fragments.name"
//        const val TARGET = "com.example.mobile_applications_project_put.fragments.target"
//    }
//}