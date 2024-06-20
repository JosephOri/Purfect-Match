package com.example.bookworms.Model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @NonNull
    var uid: String,

    var phone: String,

    var name: String,

    var email: String

): Serializable

