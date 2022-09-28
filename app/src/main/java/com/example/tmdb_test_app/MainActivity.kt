package com.example.tmdb_test_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb_test_app.core.app.App
import com.example.tmdb_test_app.core.di.FragmentComponent
import com.example.tmdb_test_app.core.factory.ViewModelFactory
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // Reference to the Login graph
    lateinit var fragmentComponent: FragmentComponent

    // Fields that need to be injected by the login graph
    @Inject lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creation of the login graph using the application graph
        fragmentComponent = (applicationContext as App)
            .appComponent.fragmentComponent().create()

        // Make Dagger instantiate @Inject fields in LoginActivity
        fragmentComponent.inject(this)

        // Now loginViewModel is available


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MovieFragment>(R.id.fragment_container_view)
            }
        }


    }
}