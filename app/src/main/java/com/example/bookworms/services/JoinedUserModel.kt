package com.example.bookworms.services

import com.example.bookworms.BookWormsApp
import com.example.bookworms.models.firebaseModel.UserFirebaseModel
import com.example.bookworms.models.entities.User
import com.example.bookworms.models.ModelRoom.UserRoomModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinedUserModel {

    private val userRoomModel: UserRoomModel = UserRoomModel()
    private val userFirebaseModel: UserFirebaseModel = UserFirebaseModel()
    private lateinit var auth: FirebaseAuth


    fun register(user: User, password: String,callback: (Boolean) -> Unit ){
        auth = Firebase.auth
        userFirebaseModel.register(user.email,password){ isSuccessful ->
            if(isSuccessful){
                val uid =auth.currentUser?.uid
                userFirebaseModel.userCollection(user.email,user.uid,user.name,user.phone){ success->
                    if(success){
                        BookWormsApp.getExecutorService().execute{
                            if (uid != null) {
                                user.uid=uid
                                userRoomModel.addUser(user)
                            }
                        }
                    }
                    callback(success)

                }
            }
            callback(isSuccessful)
        }

    }
}