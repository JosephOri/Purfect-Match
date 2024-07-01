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

    fun userCollection(uid: String, name: String, email: String,
                       phone:String, profileImg: String, callback: (Boolean) -> Unit) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document(uid)
        val data = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "profileImg" to profileImg
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
                if (documentSnapshot.data != null) {
                    val name = documentSnapshot.getString("name") ?: ""
                    val email = documentSnapshot.getString("email") ?: ""
                    val phone = documentSnapshot.getString("phone") ?: ""
                    val profileImg = documentSnapshot.getString("profileImg") ?: ""
                    val user = User(uid, name, email, phone, profileImg)
                    callback(user)
                } else {
                    Log.d("UserFirebaseModel", "getUserByUid() - User not found")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("UserFirebaseModel", "Error fetching user document: ${exception.message}")
                callback(null)
            }
    }


}