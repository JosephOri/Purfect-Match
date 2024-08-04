package com.purfectmatch.ViewModel

import androidx.lifecycle.ViewModel
import com.purfectmatch.Model.Entities.UserEntity
import com.purfectmatch.Model.JoinedModel.JoinedUserModel

class RegisterUserViewModel: ViewModel() {
    val UserModel = JoinedUserModel()

    fun register(user : UserEntity, password: String,  callback: (Boolean) -> Unit){
        UserModel.register(user, password, callback)

    }
}