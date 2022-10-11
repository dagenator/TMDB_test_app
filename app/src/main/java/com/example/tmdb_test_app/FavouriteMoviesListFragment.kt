package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.DBMovie
import com.example.tmdb_test_app.presenter.FavouriteListAdapter
import com.example.tmdb_test_app.presenter.MainViewModel
import javax.inject.Inject

class FavouriteMoviesListFragment @Inject constructor() : Fragment(R.layout.favourite_movies_list) {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var config: Config

    private val observePopularMovies = Observer<List<DBMovie>?> {
        setFavouriteUi(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
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
                val adapter = FavouriteListAdapter(movies.toTypedArray(), this)
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(context)
            }
            if (movies == null) {
                val error = it.findViewById<TextView>(R.id.favourite_list_error)
                error.visibility = View.VISIBLE
                error.text = getString(R.string.none_favourite)
            }
        }
    }

}