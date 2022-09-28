package com.example.tmdb_test_app.core.di

import android.content.Context
import com.example.tmdb_test_app.data.models.Config
import dagger.Module
import dagger.Provides

@Module()
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideConfig(): Config {
        return Config("e0d4f6aeb2f3148d80695c26581108ce", "https://image.tmdb.org/t/p/w1280")
    }



}