package com.example.tmdb_test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.tmdb_test_app.core.app.App
import com.example.tmdb_test_app.core.factory.ViewModelFactory
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.data.utils.Status
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels<MainViewModel> { viewModelFactory }

    val observeGetMovie = Observer<Resource<Movie>>{
        it?.let {
            when(it.status){
                Status.LOADING->{
                    Log.i("mAct", "loading ")
                }
                Status.ERROR->{
                    Log.i("mAct", "error ${it.message}")
                }
                Status.SUCCESS->{
                    Log.i("mAct", "${it.data}")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)

        viewModel.getMovieById(115).observe(this, observeGetMovie)
    }
}