package com.example.tmdb_test_app.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb_test_app.domain.interfaces.GetMovieByIdUseCase
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    val getMovieByIdUseCase: GetMovieByIdUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getMovieByIdUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}