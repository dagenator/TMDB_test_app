package com.example.tmdb_test_app.core.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb_test_app.core.bd.MovieDao
import com.example.tmdb_test_app.core.bd.MovieFavouriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BDModule {

    @Singleton
    @Provides
    fun provideFavoritesBD(context:Context): MovieFavouriteDatabase {
        val db = Room.databaseBuilder(
            context,
            MovieFavouriteDatabase::class.java, "database-name"
        ).build()
        return db
    }

    @Singleton
    @Provides
    fun provideMovieDAO(db: MovieFavouriteDatabase): MovieDao {
        return db.movieDao()
    }
}