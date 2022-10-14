package com.example.tmdb_test_app

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.tmdb_test_app.core.app.App
import com.example.tmdb_test_app.core.di.FragmentComponent
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        fragmentComponent = (applicationContext as App)
            .appComponent.fragmentComponent().create()

        fragmentComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonsAction()
    }

    fun setButtonsAction() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<ImageView>(R.id.to_favourite_button).setOnClickListener {
            navController.navigate(R.id.favouriteMoviesListFragment)
        }
        findViewById<ImageView>(R.id.to_popular_button).setOnClickListener {
            navController.navigate(R.id.popularFragment)
        }

    }

}