package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.MainActivity
import com.example.tmdb_test_app.core.app.App
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ SubcomponentsModule::class, AppModule::class,  NetworkModule::class, UseCaseBindModule::class])// DBModule::class])
interface AppComponent : AndroidInjector<App> {
    //fun inject(mainActivity: MainActivity)

    fun fragmentComponent():FragmentComponent.Factory
}

