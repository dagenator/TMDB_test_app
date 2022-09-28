package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.domain.impl.GetMovieAndCastByIdUseCaseImpl
import com.example.tmdb_test_app.domain.interfaces.GetMovieAndCastByIdUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseBindModule {
    @Binds
    fun bindGetMovie(getMovieByIdUseCase: GetMovieAndCastByIdUseCaseImpl): GetMovieAndCastByIdUseCase
}