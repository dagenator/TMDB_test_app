package com.example.tmdb_test_app.data.repository

import android.util.Log
import com.example.tmdb_test_app.core.bd.MovieDao
import com.example.tmdb_test_app.core.retrofit.TMDBApiService
import com.example.tmdb_test_app.data.models.*
import com.example.tmdb_test_app.data.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    val tmdbApiService: TMDBApiService,
    val config: Config,
    val movieDao: MovieDao
) {

    suspend fun getMovieAndCastById(id: Long) =
        flow<Resource<MovieAndCast>> {
            emit(Resource.loading(data = null))
            val result = CoroutineScope(Dispatchers.IO).async {
                val movie = tmdbApiService.getMovieByID(id, config.apiKey)
                val cast = tmdbApiService.getMovieCastAndCrewByID(id, config.apiKey)
                return@async (MovieAndCast(movie, cast))
            }.await()
            emit(Resource.success(result))
            Log.i("movieSearch", "getMovieAndCastById: ${result.movie.title}")
        }.catch { e ->
            emit(Resource.error(message = e.message ?: "Error Occurred!"))
        }

    suspend fun getPopularMovies(page: Int) =
        flow<Resource<PopularMovies>> {
            emit(Resource.loading(data = null))

            val movies = CoroutineScope(Dispatchers.IO).async {
                return@async tmdbApiService.getPopularMovies(config.apiKey, page)
            }.await()

            emit(Resource.success(movies))
        }.catch { e ->
            emit(Resource.error(message = e.message ?: "Error Occurred!"))
        }

    suspend fun getFavouriteMovies(): List<DBMovie> {
        return withContext(Dispatchers.IO) {
            return@withContext movieDao.getAll()
        }
    }

    suspend fun addFavourite(movie: Movie) {
        withContext(Dispatchers.IO) {
            if(!isFavouriteCheck(movie.id)){
                val bdMovie = DBMovie(
                    id = movie.id,
                    adult = movie.adult,
                    budget = movie.budget, genres = movie.genres.joinToString{ x-> x.name},
                    homepage = movie.homepage,
                    imdbID = movie.imdbID,
                    originalLanguage = movie.originalLanguage,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    popularity = movie.popularity,
                    posterPath = movie.posterPath,
                    releaseDate = movie.releaseDate,
                    revenue = movie.revenue,
                    runtime = movie.runtime,
                    status = movie.status,
                    tagline = movie.tagline,
                    title = movie.title,
                    voteAverage = movie.voteAverage,
                    voteCount = movie.voteCount
                )
                movieDao.insertMovie(bdMovie)
            }
        }
    }

    suspend fun deleteFavourite(id: Long) {
        withContext(Dispatchers.IO) {
            movieDao.delete(id)
        }
    }

    suspend fun isFavouriteCheck(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val res = movieDao.getbyId(id)
            return@withContext res != null
        }
    }

}