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
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.GetMovieAndCastByIdUseCase
import com.example.tmdb_test_app.domain.interfaces.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(
    val getMovieAndCastByIdUseCase: GetMovieAndCastByIdUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val popularMoviesPageSource: PopularMoviesPageSource
) :
    ViewModel() {

    val movie = MutableLiveData<Resource<MovieAndCast>>()
    //val popular = MutableLiveData<Resource<PopularMovies>>()


    val popular = Pager(PagingConfig(pageSize = 20)) {
        popularMoviesPageSource
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty() )


    fun getMovieAndCastById(id: Int) {
        viewModelScope.launch {
            getMovieAndCastByIdUseCase(id)
                .collect {
                    movie.value = it
                    Log.i("tag", "getMovieById: $it")
                }

        }
    }

    fun getPopularMovies(nextPage: Boolean? = null) {
//        viewModelScope.launch {
//            var page = 0
//            popular.value?.data?.let {
//                if(nextPage == true){
//                    page = (it.page + 1).toInt()
//                }
//                if(nextPage == false){
//                    page = (it.page -1).toInt()
//                }
//            }
//
//            getPopularMoviesUseCase(page).collect() {
//                popular.value = it
//            }
//        }
    }


}