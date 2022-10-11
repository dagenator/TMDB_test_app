package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Movie

interface AddFavouriteMovieUseCase {
    suspend operator fun invoke(movie: Movie)
}