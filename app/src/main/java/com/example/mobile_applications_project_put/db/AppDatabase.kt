package com.example.mobile_applications_project_put.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobile_applications_project_put.db.entities.Muscle
import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutExerciseCrossRef


@Database(entities = [Muscle::class,Exercise::class, Workout::class, WorkoutExerciseCrossRef::class], version = 2, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun muscleDao(): MuscleDao
    abstract fun exerciseDao(): ExersiceDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutWithExercisesDao(): WorkoutWithExercisesDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "exercises_db"
                ).fallbackToDestructiveMigration().build() // fallbackToDestructiveMigration will recreate the database if the version has increased
                INSTANCE = instance
                instance
            }
        }
    }
}
