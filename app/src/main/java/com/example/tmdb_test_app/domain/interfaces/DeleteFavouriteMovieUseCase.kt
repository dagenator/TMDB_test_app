package com.example.tmdb_test_app.domain.interfaces

interface DeleteFavouriteMovieUseCase {
    suspend operator fun invoke(id: Long)
}