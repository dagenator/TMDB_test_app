package com.example.tmdb_test_app.data.models

import com.google.gson.annotations.SerializedName

data class ProductionCountry (
    @SerializedName("iso_3166_1")
    val iso3166_1: String,

    val name: String
)