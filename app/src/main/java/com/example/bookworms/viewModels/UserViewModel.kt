package com.example.bookworms.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworms.models.entities.User
import com.example.bookworms.services.JoinedUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel :ViewModel(){
    private val joinedUserModel: JoinedUserModel = JoinedUserModel()

    fun register(user: User, password: String,callback:(Boolean)->Unit){
        joinedUserModel.register(user, password, callback)
    }

    fun getUserByUid(uid: String, callback: (User) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            joinedUserModel.getUserByUid(uid) { user ->
                viewModelScope.launch(Dispatchers.Main) {
                    callback(user)
                }
            }
        }
    }

    fun logout(callback: ()-> Unit){
        joinedUserModel.logout()
        callback()
    }

}