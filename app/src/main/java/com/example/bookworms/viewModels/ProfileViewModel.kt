package com.example.bookworms.viewModels

import androidx.lifecycle.ViewModel
import com.example.bookworms.models.entities.User
import com.example.bookworms.services.JoinedUserModel


class ProfileViewModel: ViewModel() {

        private val usersModel = JoinedUserModel()

        fun getUserByUid(uid :String,  callback: (User)-> Unit){
            return usersModel.getUserByUid(uid, callback)
        }
}