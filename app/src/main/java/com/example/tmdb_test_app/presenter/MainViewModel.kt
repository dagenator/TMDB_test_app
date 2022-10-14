package com.example.tmdb_test_app.presenter

import android.util.Log
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.core.di.ActivityScope
import com.example.tmdb_test_app.core.pagination.PopularMoviesPageSource
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.*
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
        viewModelScope.launch {
            favourite.value = getFavouriteMoviesUseCase()
        }
    }

    fun addFavouriteMovie(movie: Movie) {
        viewModelScope.launch {
            addFavouriteMovieUseCase(movie)
        }
    }

    fun deleteFavouriteMovie(id: Long) {
        viewModelScope.launch {
            deleteFavouriteMovieUseCase(id)
        }
    }

    fun checkIsFavourite(id: Long) {
        viewModelScope.launch {
            isMovieFavourite.value = isFavouriteMovieCheckUseCase(id) ?: false
        }
    }

    fun navigateToMovieById(id: Long, fragment: Fragment) {
        val navController = fragment.findNavController()
        val bundle = bundleOf("movieId" to id)
        navController.navigate(R.id.movieFragment, bundle)
    }

    fun loadImage(url: String? = null, fragment: Fragment, width:Int, height:Int, imageView: ImageView){
        if (url == null){
            Glide
                .with(fragment)
                .load(error_image)
                .apply(RequestOptions().override(100, 200))
                .into(imageView)
        }else {
            Glide
                .with(fragment)
                .load(url)
                .error(error_image)
                .apply(RequestOptions().override(100, 200))
                .into(imageView)
        }
    }

    companion object{
        const val error_image = R.drawable.no_image
    }
}