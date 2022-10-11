package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.IsFavouriteMovieCheckUseCase
import javax.inject.Inject

class IsFavouriteMovieCheckUseCaseImpl @Inject constructor(var tmdbRepository: TMDBRepository) : IsFavouriteMovieCheckUseCase {
    override suspend fun invoke(id: Long): Boolean =tmdbRepository.isFavouriteCheck(id)
}