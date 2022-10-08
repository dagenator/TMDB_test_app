package com.example.tmdb_test_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.tmdb_test_app.core.app.App
import com.example.tmdb_test_app.core.di.FragmentComponent
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // Reference to the Login graph
    lateinit var fragmentComponent: FragmentComponent

    // Fields that need to be injected by the login graph
    @Inject
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentComponent = (applicationContext as App)
            .appComponent.fragmentComponent().create()

        fragmentComponent.inject(this)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<PopularFragment>(R.id.fragment_container_view)
            }
        }

    }
}