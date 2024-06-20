package com.example.bookworms.Model.firebaseModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserFirebaseModel {
    private lateinit var auth: FirebaseAuth

    fun register(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   onSuccess()
                } else {
                    onError()
                }
            }
    }

}