package com.example.tmdb_test_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(

    @PrimaryKey
    val id: Long,

    val name: String
)