package com.example.tmdb_test_app.data.models

data class CastAndCrew (
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Cast>
)