package com.example.bookworms.services

import com.example.bookworms.models.entities.User
import com.example.bookworms.models.firebaseModles.UserFirebaseModel
import com.example.bookworms.models.roomModels.UserRoomModel
import com.google.firebase.auth.FirebaseAuth

class UserServices {

    private val userRoomModel: UserRoomModel = UserRoomModel()
    private val userFirebaseModel: UserFirebaseModel = UserFirebaseModel()
    private lateinit var auth: FirebaseAuth


    fun register(user: User, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        auth = FirebaseAuth.getInstance()
        userFirebaseModel.register(user.email, password, {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                user.uid = uid
                userRoomModel.addUser(user)
                onSuccess()
            }
        }, {
            onError()
        })
    }
}