package com.example.tmdb_test_app.core.retrofit

import com.example.tmdb_test_app.data.models.CastAndCrew
import com.example.tmdb_test_app.data.models.Genres
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.models.PaginateMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") id: Long,
        @Query("api_key") key: String
    ): Movie

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCastAndCrewByID(
        @Path("movie_id") id: Long,
        @Query("api_key") key: String
    ): CastAndCrew

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): PaginateMovies

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): PaginateMovies

    @GET("/3/search/movie") //
    suspend fun getMovieListByQueryAndYear(
        @Query("api_key") key: String,
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("year") year: Int,
    ): PaginateMovies

    @GET("/3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") key: String
    ): Genres

}