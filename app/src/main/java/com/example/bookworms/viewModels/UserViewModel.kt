package com.example.bookworms.viewModels

import androidx.lifecycle.ViewModel
import com.example.bookworms.models.entities.User
import com.example.bookworms.services.UserServices

class UserViewModel :ViewModel(){
    val userServices: UserServices = UserServices()

    fun register(user: User, password: String,onSuccess:()->Unit, onError:()->Unit){
        userServices.register(user, password,onSuccess,onError)
    }

}