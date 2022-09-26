package com.example.tmdb_test_app.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.GetMovieByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(val getMovieByIdUseCase: GetMovieByIdUseCase) : ViewModel() {

    fun getMovieById(id: Int) = liveData<Resource<Movie>> {
        withContext(Dispatchers.IO) {
            getMovieByIdUseCase(id).collect {
                emit(it)
                Log.i("tag", "getMovieById: $it")
            }
        }
    }
}