package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Movie

interface IsFavouriteMovieCheckUseCase {
    suspend operator fun invoke(id: Long): Boolean
}