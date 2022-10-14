package com.example.tmdb_test_app.core.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.data.models.Genre

@Database(entities = [DBMovie::class, Genre::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}