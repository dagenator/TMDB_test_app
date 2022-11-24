package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.MovieAndCast
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.data.utils.Status
import com.example.tmdb_test_app.presenter.ActorsListAdapter
import com.example.tmdb_test_app.presenter.MovieViewModel
import javax.inject.Inject

class MovieFragment @Inject constructor() : Fragment(R.layout.whole_movie_fragment) {

    @Inject
    lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var config: Config


    private val observeGetMovie = Observer<Resource<MovieAndCast>> {
        it?.let {
            when (it.status) {
                Status.LOADING -> {
                    Log.i("mAct", "loading ")
                    setLoader(true)
                }
                Status.ERROR -> {
                    Log.i("mAct", "error ${it.message}")
                    showError(it.message.toString())
                    setLoader(false)
                }
                Status.SUCCESS -> {
                    setLoader(false)
                    it.data?.let {
                        setMovieUi(it)
                    }
                }
            }
        }
    }

    private val favouriteObserver = Observer<Boolean> {
        setLikeButtonUi(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getLong("movieId") ?: defaultMovieNumber

        viewModel.movie.observe(viewLifecycleOwner, observeGetMovie)
        viewModel.isMovieFavourite.observe(viewLifecycleOwner, favouriteObserver)
        viewModel.getMovieAndCastById(movieId)
    }


    private fun setMovieUi(movie: MovieAndCast) {
        view?.let {
            val image = it.findViewById<ImageView>(R.id.picture)

            it.findViewById<TextView>(R.id.name).text = movie.movie.title
            it.findViewById<TextView>(R.id.genre).text =
                movie.movie.genres.joinToString { x -> x.name }
            it.findViewById<TextView>(R.id.description).text = movie.movie.overview
            it.findViewById<TextView>(R.id.date).text = movie.movie.releaseDate
            it.findViewById<TextView>(R.id.score).text = movie.movie.voteAverage.toString()
            val adapter = ActorsListAdapter(movie.castAndCrew.cast.toTypedArray())
            val recycler = it.findViewById<RecyclerView>(R.id.actors)
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = adapter


            viewModel.loadImage(config.imageUrl + movie.movie.posterPath, this, image)

            val swipeLayout = it.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
            swipeLayout.setOnRefreshListener {
                Log.i("refresh", "onRefresh called from SwipeRefreshLayout")
                viewModel.getMovieAndCastById(movie.movie.id)
                swipeLayout.isRefreshing = false
            }

            val likeButton = it.findViewById<Button>(R.id.addToFavouriteButton)
            viewModel.checkIsFavourite(movie.movie.id)

            likeButton.setOnClickListener {
                viewModel.isMovieFavourite.value.let {
                    if (it == true) viewModel.deleteFavouriteMovie(movie.movie.id)
                    else viewModel.addFavouriteMovie(movie.movie)
                }
                viewModel.checkIsFavourite(movie.movie.id)
            }
        }
    }

    private fun setLikeButtonUi(isFavourite: Boolean) {
        view?.let {
            val likeButton = it.findViewById<Button>(R.id.addToFavouriteButton)

            if (isFavourite) {
                likeButton.text = getString(R.string.blue_heart)
            } else {
                likeButton.text = getString(R.string.white_heart)
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        view?.let {
            val progressBar = it.findViewById<ProgressBar>(R.id.movie_progress_bar)
            val container = it.findViewById<ConstraintLayout>(R.id.movie_info_container)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            container.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showError(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val defaultMovieNumber: Long = 345

    }
}