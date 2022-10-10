package com.example.tmdb_test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tmdb_test_app.core.app.App
import com.example.tmdb_test_app.core.di.FragmentComponent
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent = (applicationContext as App)
            .appComponent.fragmentComponent().create()

        fragmentComponent.inject(this)

        setContentView(R.layout.activity_main)

    }

}