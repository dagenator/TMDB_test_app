package com.example.tmdb_test_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBMovie(
    @PrimaryKey val id: Long,
    val adult: Boolean,
    val budget: Long,
    val genres: String,
    val homepage: String,
    val imdbID: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long

)