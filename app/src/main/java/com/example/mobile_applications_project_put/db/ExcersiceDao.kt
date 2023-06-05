package com.example.mobile_applications_project_put.db
import androidx.room.*
//import com.example.mobile_applications_project_put.models.Meal

interface ExcersiceDao {}

//
//
//@Dao
//interface ExcersiceDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMeal(meal: Meal)
//
//    @Query("SELECT * FROM meal")
//    suspend fun getAllMeals(): List<Meal>
//
//    @Query("SELECT * FROM meal WHERE idMeal = :idMeal")
//    fun getMealById(idMeal: String): Meal?
//
//    @Delete
//    fun deleteMeal(meal: Meal)
//}