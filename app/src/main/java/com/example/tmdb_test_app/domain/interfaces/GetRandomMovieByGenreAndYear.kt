package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetRandomMovieByGenreAndYear {
    suspend operator fun invoke(genre: String, year: Int): Flow<Resource<Movie>>
}