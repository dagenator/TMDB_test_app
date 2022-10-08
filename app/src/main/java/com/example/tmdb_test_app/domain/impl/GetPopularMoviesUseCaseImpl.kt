package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.models.PopularMovies
import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository) :
    GetPopularMoviesUseCase {
    override suspend fun invoke(page: Int): Flow<Resource<PopularMovies>> =
        tmdbRepository.getPopularMovies(page)
}