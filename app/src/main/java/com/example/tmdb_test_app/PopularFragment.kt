package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.PaginateMovies
import com.example.tmdb_test_app.data.utils.Resource
import com.example.tmdb_test_app.data.utils.Status
import com.example.tmdb_test_app.presenter.MainViewModel
import com.example.tmdb_test_app.presenter.MovieListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularFragment @Inject constructor() : Fragment(R.layout.movies_list_fragment) {
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var config: Config

    private val observePopular = Observer<Resource<PaginateMovies>> {
        it?.let {
            when (it.status) {
                Status.LOADING -> {
                    setLoader(true)
                }
                Status.ERROR -> {
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

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieListAdapter(
            config,
            { x -> viewModel.navigateToMovieById(x, this) },
            { url, image, width, height -> viewModel.loadImage(url, this, width, height, image) })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.movie_list)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popular.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setMovieUi(movie: PaginateMovies) {
        view?.let {
//            val swipeLayout = it.findViewById<SwipeRefreshLayout>(R.id.movie_list_refresh)
//            swipeLayout.setOnRefreshListener {
//                //todo reLoad
//                swipeLayout.isRefreshing = false
//            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        view?.let {
            val progressBar = it.findViewById<ProgressBar>(R.id.movie_list_progress_bar)
            val recycler = it.findViewById<RecyclerView>(R.id.movie_list)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recycler.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showError(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}