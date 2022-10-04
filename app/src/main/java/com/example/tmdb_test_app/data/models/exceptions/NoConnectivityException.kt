package com.example.tmdb_test_app.data.models.exceptions

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}