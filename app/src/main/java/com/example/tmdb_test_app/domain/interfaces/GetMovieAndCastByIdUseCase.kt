package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetMovieAndCastByIdUseCase {
    suspend operator fun invoke(id: Int): Flow<Resource<MovieAndCast>>
}