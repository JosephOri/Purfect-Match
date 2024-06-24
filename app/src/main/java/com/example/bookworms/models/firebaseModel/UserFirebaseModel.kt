package com.example.bookworms.models.firebaseModel

import android.util.Log
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
        val docRef = db.collection("users").document(uid)
        val data = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "uid" to uid
        )
        docRef.set(data).addOnSuccessListener {
            Log.d("UserFirebaseModel", "User uploaded successfully with UID: $uid")
            callback(true)
        }.addOnFailureListener { exception ->
            Log.d("UserFirebaseModel", "Error uploading user: ${exception.message}")
            callback(false)
        }
    }

    fun getUserByUid(uid: String, callback: (User?) -> Unit) {
        val db = Firebase.firestore
        val userDocument = db.collection("users").document(uid)
        Log.d("UserFirebaseModel", "Getting user by UID: $uid")

        userDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d("UserFirebaseModel", "snapshot data: ${documentSnapshot.data}")
                if (documentSnapshot.exists()) {
//                    val documentSnapshot = querySnapshot.documents.first()
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
                    Log.d("UserFirebaseModel", "No document found with UID $uid")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("UserFirebaseModel", "Error fetching user document: ${exception.message}")
                callback(null)
            }
    }


}