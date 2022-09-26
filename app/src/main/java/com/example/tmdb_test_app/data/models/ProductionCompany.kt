package com.example.tmdb_test_app.data.models

import com.google.gson.annotations.SerializedName

data class ProductionCompany (
    val id: Long,

    @SerializedName( "logo_path")
    val logoPath: String? = null,

    val name: String,

    @SerializedName("origin_country")
    val originCountry: String
)
