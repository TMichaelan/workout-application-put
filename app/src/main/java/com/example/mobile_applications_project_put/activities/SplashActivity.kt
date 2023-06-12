package com.example.mobile_applications_project_put.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebaseList
import com.example.mobile_applications_project_put.functions.FirebaseUtility


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000
    private var username: String? = null
    private lateinit var workouts_list: List<WorkoutFirebaseList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_splash)

        val sharedPref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        username = sharedPref?.getString("username", null)

        Log.d("test", username!!)

        FirebaseUtility.getUserWorkouts(username!!) { workouts, error ->
            if (workouts != null) {
                workouts_list = workouts
            }
        }

        val logoImageView = findViewById<View>(R.id.logo_image_view)

        val rotationAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
        }

        logoImageView.startAnimation(rotationAnimation)


        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Handler().postDelayed({
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    logoImageView.clearAnimation()
                    finish()
                }, SPLASH_DELAY)
            }
        })

    }
}