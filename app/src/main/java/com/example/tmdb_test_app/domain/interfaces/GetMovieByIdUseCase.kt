package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetMovieByIdUseCase {
    suspend operator fun invoke(id:Int): Flow<Resource<Movie>>
}