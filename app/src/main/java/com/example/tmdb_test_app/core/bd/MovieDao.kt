package com.example.tmdb_test_app.core.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb_test_app.data.models.DBMovie

@Dao
interface MovieDao {
    @Query("SELECT * FROM DBMovie")
    fun getAll(): List<DBMovie>

    @Insert
    fun insertMovie(vararg bdMovie: DBMovie)

    @Query("DELETE FROM DBMovie WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM DBMovie WHERE id = :id")
    fun getbyId(id: Long):DBMovie?
}