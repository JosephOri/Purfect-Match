package com.example.bookworms.models.firebaseModel

import com.example.bookworms.models.entities.User
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
        email: String,
        uid: String,
        name: String,
        phone:String,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document()
        val data = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "uid" to uid
        )
        docRef.set(data).addOnSuccessListener {
            println("User uploaded successfully")
            callback(true)
        }.addOnFailureListener { exception ->
            println("Error uploading user: ${exception.message}")
            callback(false)
        }
    }

    fun getUserByUid(uid: String, callback: (User?) -> Unit) {
        val db = Firebase.firestore
        val usersCollection = db.collection("users")
        println("Getting user by UID: $uid")

        usersCollection.whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    println("Document found with UID $uid. querySnapshot is not empty.")
                    val documentSnapshot = querySnapshot.documents.first()
                    val userName = documentSnapshot.getString("name") ?: ""
                    val email = documentSnapshot.getString("email") ?: ""
                    val phone = documentSnapshot.getString("phone") ?: ""

                    val userEntity = User(
                        uid = uid,
                        name = userName,
                        email = email,
                        phone = phone
                    )

                    callback(userEntity)
                } else {
                    println("No document found with UID $uid. querySnapshot is empty.")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting user by UID: ${exception.message}")
                callback(null)
            }
    }


}