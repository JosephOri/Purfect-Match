package com.example.bookworms.Model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class BookEntity (
//    TODO: change this entity based on the API response and how we want to store a book returned from the API.
    @PrimaryKey
    var uid: String,

    var title: String ,
    var author: String,
    var image: String ,
    var genres: String,
    var rating: String,
    var description: String
): Serializable