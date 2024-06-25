package com.example.bookworms.Model.firebaseModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserFirebaseModel {
    private lateinit var auth: FirebaseAuth

    fun register(email: String, password: String, callback: (Boolean) -> Unit) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User registration successful
                    callback(true)
                } else {
                    // User registration failed
                    val exception = task.exception
                    println("Registration failed: ${exception?.message}")
                    callback(false)
                }

            }

    }
    fun userCollection(
        uid: String,
        name: String,
        email: String,
        phone:String,
        profileImg: String,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document()
        val data = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "profileImg" to profileImg
        )
        docRef.set(data).addOnSuccessListener {
            println("User uploaded successfully")
            callback(true)
        }.addOnFailureListener { exception ->
            println("Error uploading user: ${exception.message}")
            callback(false)
        }
    }

}