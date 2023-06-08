package com.example.mobile_applications_project_put.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_applications_project_put.R

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import com.example.cookbook.models.MealRepository




class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImageView = findViewById<ImageView>(R.id.logo_image_view)

        // Анимация с использованием ObjectAnimator
        val rotationAnimator = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f)
        rotationAnimator.duration = 1000
        rotationAnimator.interpolator = DecelerateInterpolator()

        rotationAnimator.start()
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)

        // Задержка перед переходом на главный экран
//        CoroutineScope(Dispatchers.Main).launch {
//            MealRepository.getRandomMeal { mealList ->
//
//                val intent = Intent(this@SplashActivity, MainActivity::class.java)
//                intent.putExtra("mealList", mealList)
//                startActivity(intent)
//
//                finish()
//            }
//        }
    }
}