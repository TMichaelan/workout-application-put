package com.example.mobile_applications_project_put.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.mobile_applications_project_put.models.Meal


abstract class AppDatabase : RoomDatabase(){}

//@Database(entities = [Meal::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun mealDao(): MealDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "cookbook-db"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//}