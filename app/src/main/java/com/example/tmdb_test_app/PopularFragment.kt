package com.example.tmdb_test_app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tmdb_test_app.data.models.Config
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

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieListAdapter(
            config,
            { x -> viewModel.navigateToMovieById(x, this) },
            { url, image, width, height -> viewModel.loadImage(url, this, image, width, height) })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).fragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
        val recycler = view.findViewById<RecyclerView>(R.id.movie_list)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popular.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setUi() {
        view?.let {
            val swipeLayout = it.findViewById<SwipeRefreshLayout>(R.id.movie_list_refresh)
            swipeLayout.setOnRefreshListener {
                //adapter.refresh()
                swipeLayout.isRefreshing = false
            }
        }
    }

}