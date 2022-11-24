package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.presenter.FavouriteListAdapter
import com.example.tmdb_test_app.presenter.MovieViewModel
import javax.inject.Inject

class FavouriteMoviesListFragment @Inject constructor() : Fragment(R.layout.favourite_movies_list) {

    @Inject
    lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var config: Config

    private val observePopularMovies = Observer<List<DBMovie>?> {
        setFavouriteUi(it)
        setLoader(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
        setLoader(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favourite.observe(viewLifecycleOwner, observePopularMovies)
        viewModel.getFavouriteMoviesFromDB()

    }

    private fun setFavouriteUi(movies: List<DBMovie>?) {
        view?.let {
            movies?.let { movies ->
                val recycler = it.findViewById<RecyclerView>(R.id.favourite_list)
                val adapter = FavouriteListAdapter(movies.toTypedArray()) { x ->
                    viewModel.navigateToMovieById(x, this)
                }
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(context)
            }
            if (movies == null || movies.isEmpty()) {
                val error = it.findViewById<TextView>(R.id.favourite_list_error)
                error.visibility = View.VISIBLE
                error.text = getString(R.string.none_favourite)
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        view?.let {
            val progressBar = it.findViewById<ProgressBar>(R.id.favourite_progress_bar)
            val container = it.findViewById<RecyclerView>(R.id.favourite_list)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            container.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }


}