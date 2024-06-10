package com.example.bookworms.Model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class BookEntity (

    @PrimaryKey
    var uid: String,

    var title: String ,
    var author: String,
    var image: String ,
    var genres: String,
    var rating: String,
    var description: String
): Serializable