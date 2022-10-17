package com.example.tmdb_test_app.domain.impl

import com.example.tmdb_test_app.data.models.Genre
import com.example.tmdb_test_app.data.repository.TMDBRepository
import com.example.tmdb_test_app.domain.interfaces.GetGenresListUseCase
import javax.inject.Inject

class GetGenresListUseCaseImpl @Inject constructor(val tmdbRepository: TMDBRepository) :
    GetGenresListUseCase {
    override suspend fun invoke(): List<Genre> = tmdbRepository.getGenres()
}