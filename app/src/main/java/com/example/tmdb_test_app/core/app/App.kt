package com.example.tmdb_test_app.core.app

import android.app.Application
import com.example.tmdb_test_app.core.di.AppComponent
import com.example.tmdb_test_app.core.di.AppModule
import com.example.tmdb_test_app.core.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()

    }
}