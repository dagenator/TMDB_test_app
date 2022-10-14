package com.example.tmdb_test_app.data.models

import com.google.gson.annotations.SerializedName

data class PaginateMovies (
    val page: Long,
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)