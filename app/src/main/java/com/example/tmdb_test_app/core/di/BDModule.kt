package com.example.tmdb_test_app.core.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb_test_app.core.bd.GenreDao
import com.example.tmdb_test_app.core.bd.MovieDao
import com.example.tmdb_test_app.core.bd.MovieDatabase
import com.example.tmdb_test_app.data.models.Genre
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BDModule {

    @Singleton
    @Provides
    fun provideFavoritesBD(context:Context): MovieDatabase {
        val db = Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "database-name"
        ).build()
        return db
    }

    @Singleton
    @Provides
    fun provideMovieDAO(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Singleton
    @Provides
    fun provideGenreDAO(db: MovieDatabase): GenreDao {
        return db.genreDao()
    }
}