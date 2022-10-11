package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.GetFavouriteMoviesUseCase
import javax.inject.Inject

class GetFavouriteMoviesUseCaseImpl  @Inject constructor(val tmdbRepository: TMDBRepository): GetFavouriteMoviesUseCase {
    override suspend fun invoke(): List<DBMovie> = tmdbRepository.getFavouriteMovies()
}