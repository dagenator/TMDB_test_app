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
import com.example.tmdb_test_app.core.pagination.TopRatedMoviesPageSource
import com.example.tmdb_test_app.core.pagination.PopularMoviesPageSource
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.data.models.Genre
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.domain.interfaces.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class MovieViewModel @Inject constructor(
    private val getMovieAndCastByIdUseCase: GetMovieAndCastByIdUseCase,
    private val popularMoviesPageSource: PopularMoviesPageSource,
    private val topRatedMoviesPageSource: TopRatedMoviesPageSource,
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val addFavouriteMovieUseCase: AddFavouriteMovieUseCase,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCase,
    private val isFavouriteMovieCheckUseCase: IsFavouriteMovieCheckUseCase,
    private val genresListUseCase: GetGenresListUseCase,
    private val randomMovieByGenreAndYear: GetRandomMovieByGenreAndYear
) :
    ViewModel() {
    val movie = MutableLiveData<Resource<MovieAndCast>>()
    val favourite = MutableLiveData<List<DBMovie>?>()
    val isMovieFavourite = MutableLiveData<Boolean>(false)
    val genres = MutableLiveData<List<Genre>>()
    val randomMovie = MutableLiveData<Resource<Movie>>()

    val popular = Pager(PagingConfig(pageSize = 20)) {
        popularMoviesPageSource
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    val topRated = Pager(PagingConfig(pageSize = 20)) {
        topRatedMoviesPageSource
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

    fun loadImage(url: String? = null, fragment: Fragment,  imageView: ImageView, width:Int = 100, height:Int= 200){
        if (url == null){
            Glide
                .with(fragment)
                .load(error_image)
                .apply(RequestOptions().override(width, height))
                .into(imageView)
        }else {
            Glide
                .with(fragment)
                .load(url)
                .error(error_image)
                .apply(RequestOptions().override(width, height))
                .into(imageView)
        }
    }

    fun uploadGenres(){
        viewModelScope.launch {
            genres.value = genresListUseCase() ?: emptyList()
        }
    }

    fun getRandomMovie(genre: String, year:Int){
        Log.i("randomMovie", "getRandomMovie: $genre , $year ")
        viewModelScope.launch {
            randomMovieByGenreAndYear(genre, year).collect{
                randomMovie.value = it
            }
        }
    }

    companion object{
        const val error_image = R.drawable.no_image
    }
}