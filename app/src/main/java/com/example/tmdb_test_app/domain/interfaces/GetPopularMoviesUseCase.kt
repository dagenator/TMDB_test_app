package com.example.tmdb_test_app.domain.interfaces

import android.graphics.pdf.PdfDocument
import com.example.tmdb_test_app.data.models.PopularMovies
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {
    suspend operator fun invoke(page: Int): Flow<Resource<PopularMovies>>
}