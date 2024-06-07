package com.example.bookworms.models.roomModel

import androidx.lifecycle.LiveData
import com.example.bookworms.models.entities.User

class UserModel {

     fun addUser(user: User){
        val db = AppLocalDB.getInstance().userDao().addUser(user)
     }
    fun getUserById(uid: Int): LiveData<User> {
        return AppLocalDB.getInstance().userDao().getUserById(uid)
    }
    fun getUserByEmail(email: String): User {
        return AppLocalDB.getInstance().userDao().getUserByEmail(email)
    }

    fun updateUser(user: User){
        return AppLocalDB.getInstance().userDao().updateUser(user)
    }


}