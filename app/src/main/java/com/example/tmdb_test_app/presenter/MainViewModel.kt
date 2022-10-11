package com.example.tmdb_test_app.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb_test_app.core.di.ActivityScope
import com.example.tmdb_test_app.core.pagination.PopularMoviesPageSource
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(
    val getMovieAndCastByIdUseCase: GetMovieAndCastByIdUseCase,
    val popularMoviesPageSource: PopularMoviesPageSource,
    val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    val addFavouriteMovieUseCase: AddFavouriteMovieUseCase,
    val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCase,
    val isFavouriteMovieCheckUseCase: IsFavouriteMovieCheckUseCase
) :
    ViewModel() {

    val movie = MutableLiveData<Resource<MovieAndCast>>()
    val favourite = MutableLiveData<List<DBMovie>?>()
    val isMovieFavourite = MutableLiveData<Boolean>(false)

    val popular = Pager(PagingConfig(pageSize = 20)) {
        popularMoviesPageSource
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)


    fun getMovieAndCastById(id: Long) {
        viewModelScope.launch {
            getMovieAndCastByIdUseCase(id)
                .collect {
                    movie.value = it
                    Log.i("tag", "getMovieById: $it")
                }

        }
    }

    fun getFavouriteMoviesFromDB() {
        viewModelScope.launch{
            favourite.value = getFavouriteMoviesUseCase()
        }
    }

    fun addFavouriteMovie(movie: Movie){
        viewModelScope.launch {
            addFavouriteMovieUseCase(movie)
        }
    }

    fun deleteFavouriteMovie(id: Long){
        viewModelScope.launch {
            deleteFavouriteMovieUseCase(id)
        }
    }

    fun checkIsFavourite(id: Long){
         viewModelScope.launch{
             isMovieFavourite.value = isFavouriteMovieCheckUseCase(id) ?: false
        }
    }
}