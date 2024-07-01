package com.example.bookworms.models.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    var uid: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "profile_img")
    var profileImg: String

): Serializable