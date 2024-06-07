package com.example.bookworms.models.roomModel.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bookworms.models.entities.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users ORDER BY uid ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUserById(uid: Int): LiveData<User>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User
}