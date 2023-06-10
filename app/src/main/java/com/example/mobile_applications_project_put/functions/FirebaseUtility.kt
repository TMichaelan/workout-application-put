package com.example.mobile_applications_project_put.functions

import android.util.Log
import com.example.mobile_applications_project_put.db.entities.User
import com.example.mobile_applications_project_put.db.entities.Workout
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase
import com.example.mobile_applications_project_put.db.entities.WorkoutWithExercises
import com.google.firebase.database.*

object FirebaseUtility {
    private val database = FirebaseDatabase.getInstance()

    fun registerUser(user: User, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")
        val usernamesRef = database.getReference("usernames")
        val emailsRef = database.getReference("emails")

        val usernameQuery: Query = usernamesRef.orderByValue().equalTo(user.username)
        val emailQuery: Query = emailsRef.orderByValue().equalTo(user.email)

        usernameQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Username already exists
                    callback(false, "Username already exists.")
                } else {
                    emailQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // Email already exists
                                callback(false, "Email already exists.")
                            } else {
                                // Both username and email are unique, proceed with registration
                                usersRef.child(user.username).setValue(user)
                                usernamesRef.child(user.username).setValue(user.username)
                                emailsRef.child(user.username).setValue(user.email)
                                callback(true, "Registration successful.")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            callback(false, "Failed to check email: ${error.message}")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, "Failed to check username: ${error.message}")
            }
        })
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?, String) -> Unit) {
        val usersRef = database.getReference("users")

        val emailQuery: Query = usersRef.orderByChild("email").equalTo(email)

        emailQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var user: User? = null
                    for (childSnapshot in snapshot.children) {
                        user = childSnapshot.getValue(User::class.java)
                        break
                    }

                    if (user != null) {
                        if (user.password == password) {
                            onComplete(true, user.username, "Login successful")
                        } else {
                            onComplete(false, null, "Incorrect password")
                        }
                    } else {
                        onComplete(false, null, "Failed to fetch user")
                    }
                } else {
                    onComplete(false, null, "Email does not exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(false, null, "Failed to check email: ${error.message}")
            }
        })
    }

    fun addWorkout(username: String, workout: WorkoutFirebase, callback: (Boolean, String) -> Unit) {

        val usersRef = database.getReference("users").child(username)
        val userWorkoutsRef = usersRef.child("workouts")

        val key = userWorkoutsRef.push().key
        if (key == null) {
            callback(false, "Could not generate a unique key for the workout")
            return
        }

        workout.id = key

        userWorkoutsRef.child(key).setValue(workout)
            .addOnSuccessListener { callback(true, "Workout successfully added") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }

//    fun getWorkoutsFromUser(username: String, callback: (List<WorkoutFirebase>?, String?) -> Unit) {
//        val userWorkoutsRef = database.getReference("users").child(username).child("workouts")
//
//        userWorkoutsRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val workouts = mutableListOf<WorkoutFirebase>()
//                for (workoutSnapshot in snapshot.children) {
//                    val workout = workoutSnapshot.getValue(WorkoutFirebase::class.java)
//                    workout?.let {
//                        workouts.add(it)
//                    }
//                }
//                callback(workouts, null)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                callback(null, error.message)
//            }
//        })
//    }

    fun getUserWorkouts(username: String, callback: (List<WorkoutFirebase>?, String?) -> Unit){

        val userWorkoutsRef = database.getReference("users").child(username).child("workouts")

        userWorkoutsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<WorkoutFirebase>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(WorkoutFirebase::class.java)
                    workout?.let {
                        workouts.add(it)
                    }
                }
                callback(workouts, null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, error.message)
            }
        })

    }


    fun deleteWorkout(username: String, workoutId: String, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users").child(username)
        val userWorkoutsRef = usersRef.child("workouts")

        userWorkoutsRef.child(workoutId).removeValue()
            .addOnSuccessListener { callback(true, "Workout successfully deleted") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }






}
