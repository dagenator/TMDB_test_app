package com.example.tmdb_test_app.data.repository

import android.graphics.pdf.PdfDocument
import com.example.tmdb_test_app.core.retrofit.TMDBApiService
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.models.PopularMovies
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TMDBRepository @Inject constructor(val tmdbApiService: TMDBApiService, val config: Config) {

    suspend fun getMovieAndCastById(id: Int) =
        flow<Resource<MovieAndCast>> {
            emit(Resource.loading(data = null))
            val result =  CoroutineScope(Dispatchers.IO).async {
                val movie = tmdbApiService.getMovieByID(id, config.apiKey)
                val cast = tmdbApiService.getMovieCastAndCrewByID(id, config.apiKey)
                return@async (MovieAndCast(movie, cast))
            }.await()
            emit(Resource.success(result))

        }.catch { e ->
            emit(Resource.error(message = e.message ?: "Error Occurred!"))
        }

    suspend fun getPopularMovies(page: Int) =
        flow<Resource<PopularMovies>> {
            emit(Resource.loading(data = null))

            val movies =  CoroutineScope(Dispatchers.IO).async {
                return@async tmdbApiService.getPopularMovies(config.apiKey, page)
            }.await()

            emit(Resource.success(movies))
        }.catch { e ->
            emit(Resource.error(message = e.message ?: "Error Occurred!"))
        }
}