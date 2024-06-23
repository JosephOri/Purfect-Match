package com.example.bookworms.viewModels

import androidx.lifecycle.ViewModel
import com.example.bookworms.models.entities.User
import com.example.bookworms.services.JoinedUserModel

class UserViewModel :ViewModel(){
    private val joinedUserModel: JoinedUserModel = JoinedUserModel()

    fun register(user: User, password: String,callback:(Boolean)->Unit){
        joinedUserModel.register(user, password, callback)
    }

}