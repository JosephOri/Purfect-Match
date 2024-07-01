package com.example.bookworms.models.entities

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class PostEntity(
    @PrimaryKey
    var id: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

) {
    @Ignore
    constructor() : this("", "", "")
}
