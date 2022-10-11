package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.DBMovie

interface GetFavouriteMoviesUseCase {
    suspend operator fun invoke(): List<DBMovie>
}