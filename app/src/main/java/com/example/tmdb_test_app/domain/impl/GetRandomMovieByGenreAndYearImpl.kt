package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.models.Genre
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.GetRandomMovieByGenreAndYear
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomMovieByGenreAndYearImpl @Inject constructor(val tmdbRepository: TMDBRepository) :
    GetRandomMovieByGenreAndYear {
    override suspend fun invoke(genre: String, year: Int): Flow<Resource<Movie>> =
        tmdbRepository.getRandomMovieByGenreAndYear(genre, year)
}