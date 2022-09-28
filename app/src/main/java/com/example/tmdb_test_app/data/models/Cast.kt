package com.example.tmdb_test_app.data.models

import com.google.gson.annotations.SerializedName

data class Cast (
    val adult: Boolean,
    val gender: Long,
    val id: Long,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerializedName("credit_id")
    val creditID: String,

    val order: Long? = null,
    val department: String? = null,
    val job: String? = null
)