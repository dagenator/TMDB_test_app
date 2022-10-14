package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.PaginateMovies
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {
    suspend operator fun invoke(page: Int): Flow<Resource<PaginateMovies>>
}