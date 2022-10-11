package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.DeleteFavouriteMovieUseCase
import javax.inject.Inject

class DeleteFavouriteMovieUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository): DeleteFavouriteMovieUseCase {
    override suspend fun invoke(id: Long) = tmdbRepository.deleteFavourite(id)
}