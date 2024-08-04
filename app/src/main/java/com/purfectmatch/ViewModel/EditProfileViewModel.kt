package com.purfectmatch.ViewModel

import androidx.lifecycle.ViewModel
import com.purfectmatch.Model.Entities.UserEntity
import com.purfectmatch.Model.JoinedModel.JoinedUserModel

class EditProfileViewModel: ViewModel() {
    val userModel = JoinedUserModel()
    fun editProfile(user : UserEntity, password : String, callback: (Boolean) -> Unit){
        userModel.editProfile(user,password,callback)

    }
}