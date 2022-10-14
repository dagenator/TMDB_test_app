package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Genre
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetRandomMovieByGenreAndYear {
    suspend operator fun invoke(genre: Genre, year:Int): Flow<Resource<Long>>
}