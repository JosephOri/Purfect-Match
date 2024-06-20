package com.example.bookworms.Model.ModelRoom

import androidx.lifecycle.LiveData
import com.example.bookworms.Model.entities.User

class UserRoomModel {

     fun addUser(user: User){
        AppLocalDB.getInstance().userDao().addUser(user)
     }
    fun updateUser(user: User){
        return AppLocalDB.getInstance().userDao().updateUser(user)
    }
    fun deleteUser(user: User){
        return AppLocalDB.getInstance().userDao().deleteUser(user)
    }
    fun readAllData(): LiveData<List<User>> {
        return AppLocalDB.getInstance().userDao().readAllData()
    }
    fun getUserById(uid: Int): LiveData<User> {
        return AppLocalDB.getInstance().userDao().getUserById(uid)
    }
    fun getUserByEmail(email: String): User {
        return AppLocalDB.getInstance().userDao().getUserByEmail(email)
    }

}