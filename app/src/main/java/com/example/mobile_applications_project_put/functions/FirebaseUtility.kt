package com.example.mobile_applications_project_put.functions

import com.example.mobile_applications_project_put.db.entities.User
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


}