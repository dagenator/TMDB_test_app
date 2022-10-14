package com.example.tmdb_test_app.core.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb_test_app.data.models.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM Genre")
    fun getGenres(): List<Genre>

    @Insert
    fun setGenre(vararg genre: Genre)
}