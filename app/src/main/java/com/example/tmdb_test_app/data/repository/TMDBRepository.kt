package com.example.tmdb_test_app.data.repository

import com.example.tmdb_test_app.core.retrofit.TMDBApiService
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBRepository @Inject constructor(val tmdbApiService: TMDBApiService, val config: Config){

    suspend fun getMovieById(id:Int) = flow<Resource<Movie>> {
        emit(Resource.loading(data = null))
        try {
            val answer = tmdbApiService.getMovieByID(id, config.apiKey)
            emit(Resource.success(data = answer))
        } catch (exception: Exception) {
            emit(Resource.error(message = exception.message ?: "Error Occurred!"))
        }
    }

}