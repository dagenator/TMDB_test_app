package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.domain.impl.GetMovieAndCastByIdUseCaseImpl
import com.example.tmdb_test_app.domain.impl.GetPopularMoviesUseCaseImpl
import com.example.tmdb_test_app.domain.interfaces.GetMovieAndCastByIdUseCase
import com.example.tmdb_test_app.domain.interfaces.GetPopularMoviesUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseBindModule {
    @Binds
    fun bindGetMovie(getMovieByIdUseCase: GetMovieAndCastByIdUseCaseImpl): GetMovieAndCastByIdUseCase

    @Binds
    fun bindGetPopular(GetPopularMoviesUseCase: GetPopularMoviesUseCaseImpl): GetPopularMoviesUseCase

}