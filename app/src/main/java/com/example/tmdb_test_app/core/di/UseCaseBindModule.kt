package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.domain.impl.GetMovieByIdUseCaseImpl
import com.example.tmdb_test_app.domain.interfaces.GetMovieByIdUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseBindModule {
    @Binds
    fun bindGetMovie(getMovieByIdUseCase: GetMovieByIdUseCaseImpl): GetMovieByIdUseCase
}