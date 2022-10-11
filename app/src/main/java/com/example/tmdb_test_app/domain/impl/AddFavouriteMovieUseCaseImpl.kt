package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.AddFavouriteMovieUseCase
import javax.inject.Inject

class AddFavouriteMovieUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository) :
    AddFavouriteMovieUseCase {
    override suspend fun invoke(movie: Movie) = tmdbRepository.addFavourite(movie)
}