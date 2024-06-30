package com.example.bookworms.services

import android.util.Log
import com.example.bookworms.BookWormsApp
import com.example.bookworms.Model.firebaseModel.UserFirebaseModel
import com.example.bookworms.Model.entities.User
import com.example.bookworms.Model.ModelRoom.UserRoomModel
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
                if(uid!=null) {
                    user.uid = uid
                    userFirebaseModel.userCollection(user.uid, user.name, user.email, user.phone, user.profileImg) { success ->
                        if (success) {
                            BookWormsApp.getExecutorService().execute {
                                userRoomModel.addUser(user)
                            }
                        }
                        callback(success)
                    }
                } else {
                    callback(false)
                }
            } else{
                callback(false)
            }
            callback(isSuccessful)
        }

    }

    fun getUserByUid(uid: String, callback: (User) -> Unit ){
        userFirebaseModel.getUserByUid(uid) { userEntity ->
            if (userEntity != null) {
                BookWormsApp.getExecutorService().execute {
                    userRoomModel.getUserById(uid)
                }
                callback(userEntity)
            } else {
                Log.d("JoinedUserModel", "User not found")
            }
        }
    }

    fun logout(){
        auth.signOut()
    }
}