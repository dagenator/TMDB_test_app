package com.example.tmdb_test_app.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_test_app.core.di.ActivityScope
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.GetMovieAndCastByIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(val getMovieAndCastByIdUseCase: GetMovieAndCastByIdUseCase) :
    ViewModel() {

    val movie = MutableLiveData<Resource<MovieAndCast>>()

    fun getMovieAndCastById(id: Int) {
        viewModelScope.launch {
            getMovieAndCastByIdUseCase(id)
                .collect {
                    movie.value = it
                    Log.i("tag", "getMovieById: $it")
                }

        }
    }

}