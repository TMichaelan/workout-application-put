package com.example.mobile_applications_project_put.functions

import com.example.mobile_applications_project_put.db.entities.Exercise
import com.example.mobile_applications_project_put.db.entities.User
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebase
import com.example.mobile_applications_project_put.db.entities.WorkoutFirebaseList
import com.google.firebase.database.*
import java.util.*

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

    fun addWorkout(username: String, workout: WorkoutFirebaseList, callback: (Boolean, String) -> Unit) {

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

//    fun getUserWorkouts(username: String, callback: (List<WorkoutFirebase>?, String?) -> Unit){
//
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
//
//    }

    fun getUserWorkouts(username: String, callback: (List<WorkoutFirebaseList>?, String?) -> Unit) {

        val userWorkoutsRef = database.getReference("users").child(username).child("workouts")

        userWorkoutsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<WorkoutFirebaseList>()
                for (workoutSnapshot in snapshot.children) {
                    val workoutMap = workoutSnapshot.getValue(WorkoutFirebase::class.java)
                    val workoutList = workoutMap?.let {
                        WorkoutFirebaseList(
                            id = it.id,
                            name = it.name,
                            exercises = it.exercises.values.toList()
                        )
                    }
                    workoutList?.let {
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

    fun updateUserName(username: String, newName: String, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")

        usersRef.child(username).child("name").setValue(newName)
            .addOnSuccessListener { callback(true, "Name successfully updated") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }

    fun updateUserHeight(username: String, newHeight: Int, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")

        usersRef.child(username).child("height").setValue(newHeight)
            .addOnSuccessListener { callback(true, "Height successfully updated") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }
    fun updateUserAge(username: String, newAge: Int, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")

        usersRef.child(username).child("age").setValue(newAge)
            .addOnSuccessListener { callback(true, "Age successfully updated") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }

    fun updateUserWeight(username: String, newWeight: Double, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")

        usersRef.child(username).child("weight").setValue(newWeight)
            .addOnSuccessListener { callback(true, "Weight successfully updated") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }

    fun getUser(username: String, callback: (User?, String?) -> Unit) {
        val usersRef = database.getReference("users")

        usersRef.child(username).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    callback(user, null)
                } else {
                    callback(null, "User not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, "Failed to get user: ${error.message}")
            }
        })
    }

    fun getUserWorkoutExercises(username: String, workoutId: String, callback: (List<Exercise>?, String?) -> Unit) {
        val userWorkoutExercisesRef = database.getReference("users")
            .child(username)
            .child("workouts")
            .child(workoutId)
            .child("exercises")

        userWorkoutExercisesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exercises = mutableListOf<Exercise>()
                for (exerciseSnapshot in snapshot.children) {
                    val exercise = exerciseSnapshot.getValue(Exercise::class.java)
                    exercise?.let {
                        exercises.add(it)
                    }
                }
                callback(exercises, null)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, error.message)
            }
        })
    }

    fun addExerciseToWorkout(username: String, workoutId: String, exercise: Exercise, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users").child(username)
        val workoutExercisesRef = usersRef.child("workouts").child(workoutId).child("exercises")

        // Get the count of the current exercises as the next key
        workoutExercisesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nextKey = snapshot.childrenCount
                exercise.id = nextKey.toString()

                workoutExercisesRef.child(exercise.id).setValue(exercise)
                    .addOnSuccessListener { callback(true, "Exercise successfully added") }
                    .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, "Failed to add exercise: ${error.message}")
            }
        })
    }

    fun addExercisesToWorkout(username: String, workoutId: String, exercises: List<Exercise>, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users").child(username)
        val workoutExercisesRef = usersRef.child("workouts").child(workoutId).child("exercises")

//        for (exercise in exercises) {
//             Generate a unique ID for the exercise
//            val id = UUID.randomUUID().toString()
//            val key = workoutExercisesRef.push().key ?: ""
//            exercise.id = key
//
//            workoutExercisesRef.child(key).setValue(exercise)
//                .addOnSuccessListener { callback(true, "Exercise successfully added") }
//                .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
//        }

        workoutExercisesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nextKey = snapshot.childrenCount

//                val exercise = exercises[0]

                for (exercise in exercises) {

                    exercise.id = workoutExercisesRef.push().key ?: ""
                    workoutExercisesRef.child(exercise.id).setValue(exercise)
                        .addOnSuccessListener { callback(true, "Exercise successfully added") }
                        .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                callback(false, "Failed to add exercise: ${error.message}")
            }
        })


    }

    fun removeExerciseFromWorkout(username: String, workoutId: String, exerciseId: String, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users").child(username)
        val workoutExercisesRef = usersRef.child("workouts").child(workoutId).child("exercises")

        workoutExercisesRef.child(exerciseId).removeValue()
            .addOnSuccessListener { callback(true, "Exercise successfully removed") }
            .addOnFailureListener { exception -> callback(false, exception.message ?: "Unknown error occurred") }
    }

    fun updateWorkoutName(username: String, workoutId: String, newWorkoutName: String, callback: (Boolean, String) -> Unit) {
        val usersRef = database.getReference("users")
        val userWorkoutsRef = usersRef.child(username).child("workouts")

        userWorkoutsRef.child(workoutId).child("name").setValue(newWorkoutName)
            .addOnSuccessListener { callback(true, "Workout name successfully updated") }
            .addOnFailureListener { exception ->
                callback(
                    false,
                    exception.message ?: "Unknown error occurred"
                )
            }
    }

}