package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.core.retrofit.NetworkConnectionInterceptor
import com.example.tmdb_test_app.core.retrofit.TMDBApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {
    @Provides
    fun provideApiService(networkConnectionInterceptor: NetworkConnectionInterceptor): TMDBApiService {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(networkConnectionInterceptor)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(TMDBApiService::class.java)
    }

}