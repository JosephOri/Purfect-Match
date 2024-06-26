package com.example.bookworms.viewModels

import androidx.lifecycle.ViewModel
import com.example.bookworms.Model.entities.User
import com.example.bookworms.services.JoinedUserModel

class UserViewModel :ViewModel(){
    private val joinedUserModel: JoinedUserModel = JoinedUserModel()

    fun register(user: User, password: String,callback:(Boolean)->Unit){
        joinedUserModel.register(user, password, callback)
    }

    fun getUserByUid(uid: String, callback: (User) -> Unit){
        joinedUserModel.getUserByUid(uid, callback)
    }

    fun logout(){
        joinedUserModel.logout()
    }

}