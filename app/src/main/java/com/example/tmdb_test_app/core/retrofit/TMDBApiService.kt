package com.example.tmdb_test_app.core.retrofit

import com.example.tmdb_test_app.data.models.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String
    ): Movie

}