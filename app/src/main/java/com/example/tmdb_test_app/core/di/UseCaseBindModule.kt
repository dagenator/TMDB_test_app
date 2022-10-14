package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.domain.impl.*
import com.example.tmdb_test_app.domain.interfaces.*
import dagger.Binds
import dagger.Module

@Module
interface UseCaseBindModule {
    @Binds
    fun bindGetMovie(getMovieByIdUseCase: GetMovieAndCastByIdUseCaseImpl): GetMovieAndCastByIdUseCase

    @Binds
    fun bindGetPopular(getPopularMoviesUseCase: GetPopularMoviesUseCaseImpl): GetPopularMoviesUseCase

    @Binds
    fun bindAddFavouriteMovie(addFavouriteMovieUseCase: AddFavouriteMovieUseCaseImpl): AddFavouriteMovieUseCase

    @Binds
    fun bindGetFavouriteMovies(getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl): GetFavouriteMoviesUseCase

    @Binds
    fun bindDeleteFavouriteMovie(deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCaseImpl): DeleteFavouriteMovieUseCase

    @Binds
    fun bindIsFavourite(isFavouriteMovieCheckUseCase: IsFavouriteMovieCheckUseCaseImpl): IsFavouriteMovieCheckUseCase

    @Binds
    fun bindGetGenresListUseCase(getGenresListUseCase: GetGenresListUseCaseImpl): GetGenresListUseCase

    @Binds
    fun bindGetRandomMovieByGenreAndYear(getRandomMovieByGenreAndYear: GetRandomMovieByGenreAndYearImpl): GetRandomMovieByGenreAndYear

}