package com.example.tmdb_test_app.domain.interfaces

import com.example.tmdb_test_app.data.models.Genre

interface GetGenresListUseCase {
    suspend operator fun invoke(): List<Genre>
}