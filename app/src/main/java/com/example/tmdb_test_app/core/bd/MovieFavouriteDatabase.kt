package com.example.tmdb_test_app.core.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb_test_app.data.models.DBMovie

@Database(entities = [DBMovie::class], version = 1)
abstract class MovieFavouriteDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}