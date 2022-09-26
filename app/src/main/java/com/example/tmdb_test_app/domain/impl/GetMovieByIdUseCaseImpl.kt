package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.GetMovieByIdUseCase
import javax.inject.Inject

class GetMovieByIdUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository) : GetMovieByIdUseCase {
    override suspend fun invoke(id: Int) = tmdbRepository.getMovieById(id)
}