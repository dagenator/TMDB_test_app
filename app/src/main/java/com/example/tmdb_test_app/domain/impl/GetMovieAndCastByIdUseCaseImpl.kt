package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.GetMovieAndCastByIdUseCase
import javax.inject.Inject

class GetMovieAndCastByIdUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository) :
    GetMovieAndCastByIdUseCase {
    override suspend fun invoke(id: Long) = tmdbRepository.getMovieAndCastById(id)
}