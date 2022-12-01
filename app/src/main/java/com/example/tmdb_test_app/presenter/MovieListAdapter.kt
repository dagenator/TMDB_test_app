package com.example.tmdb_test_app.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie
import com.google.android.material.progressindicator.LinearProgressIndicator

class MovieListAdapter(
    private val config: Config,
    private val click: (movieId: Long) -> Unit,
    private val imageLoader: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit,
    private val latestMoviesHolderIndex: Int,
    private val latestMoviesRecyclerInit: (recycler: RecyclerView, prepend:LinearProgressIndicator,append:LinearProgressIndicator) -> Unit
) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(PopularMovieDiffItemCallback) {

    class MovieViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val movieRowPoster: ImageView = view.findViewById(R.id.movie_row_poster)
        private val movieRowName: TextView = view.findViewById(R.id.movie_row_name)
        private val movieRowScore: TextView = view.findViewById(R.id.movie_row_score)
        private val movieRowData: TextView = view.findViewById(R.id.movie_row_data)
        private val movieRow: ConstraintLayout = view.findViewById(R.id.movie_row_wrapper)
        private val recycler: RecyclerView = view.findViewById(R.id.posters_recycler_view)
        private val recyclerWrapper: ConstraintLayout = view.findViewById(R.id.posters_recycler_view_wrapper)
        private val prepend:LinearProgressIndicator = view.findViewById(R.id.prepend_progress)
        private val append:LinearProgressIndicator = view.findViewById(R.id.append_progress)


        fun bind(
            movie: Movie?,
            click: (movieId: Long) -> Unit,
            imageLoad: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit,
            config: Config,
            showLatestRecycler: Boolean,
            latestMoviesRecyclerInit: (recycler: RecyclerView, prepend:LinearProgressIndicator,append:LinearProgressIndicator) -> Unit
        ) {
            val url = if (movie == null) null else config.imageUrl + movie.posterPath
            imageLoad(url, movieRowPoster, 100, 200)

            movieRowName.text = movie?.title
            movieRowData.text = movie?.releaseDate
            movieRowScore.text = movie?.voteAverage.toString()

            movieRow.setOnClickListener {
                movie?.id?.let { x -> click(x) }
            }

            recyclerWrapper.isVisible = showLatestRecycler
            if (showLatestRecycler) {
                latestMoviesRecyclerInit(recycler, prepend, append)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row, viewGroup, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            click,
            imageLoader,
            config,
            position == latestMoviesHolderIndex,
            latestMoviesRecyclerInit
        )
    }
}

object PopularMovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

