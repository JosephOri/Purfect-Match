package com.purfectmatch.ViewModel

import androidx.lifecycle.ViewModel
import com.purfectmatch.Model.Entities.UserEntity
import com.purfectmatch.Model.JoinedModel.JoinedUserModel

class ProfileViewModel: ViewModel() {
    val usersModel = JoinedUserModel()

    fun getUserByUid(uid :String,  callback: (UserEntity)-> Unit){
        return usersModel.getUserByUid(uid, callback)
    }
}