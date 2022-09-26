package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.MainActivity
import dagger.Component

@Component(modules = [AppModule::class,  NetworkModule::class, UseCaseBindModule::class])// DBModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}