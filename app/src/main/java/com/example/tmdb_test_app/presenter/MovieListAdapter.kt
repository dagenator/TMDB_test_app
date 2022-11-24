package com.example.tmdb_test_app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie

class MovieListAdapter(
    private val config: Config,
    private val click: (movieId: Long) -> Unit,
    private val imageLoader: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit,
    private val latestMoviesHolderIndex: Int,
    private val latestMoviesRecyclerInit: (recycler: RecyclerView) -> Unit
) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(PopularMovieDiffItemCallback) {

    class MovieViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        val movieRowPoster: ImageView
        val movieRowName: TextView
        val movieRowScore: TextView
        val movieRowData: TextView
        val movieRow: ConstraintLayout
        val recycler: RecyclerView
        val recyclerWrapper: ConstraintLayout

        init {
            movieRowPoster = view.findViewById(R.id.movie_row_poster)
            movieRowName = view.findViewById(R.id.movie_row_name)
            movieRowScore = view.findViewById(R.id.movie_row_score)
            movieRowData = view.findViewById(R.id.movie_row_data)
            movieRow = view.findViewById(R.id.movie_row_wrapper)
            recycler = view.findViewById(R.id.posters_recycler_view)
            recyclerWrapper = view.findViewById(R.id.posters_recycler_view_wrapper)
        }

        fun bind(
            movie: Movie?,
            click: (movieId: Long) -> Unit,
            imageLoad: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit,
            config: Config,
            showLatestRecycler: Boolean,
            latestMoviesRecyclerInit: (recycler: RecyclerView) -> Unit
        ) {
            val url = if (movie == null) null else config.imageUrl + movie.posterPath
            imageLoad(url, movieRowPoster, 100, 200)

            movieRowName.text = movie?.title
            movieRowData.text = movie?.releaseDate
            movieRowScore.text = movie?.voteAverage.toString()

            movieRow.setOnClickListener {
                movie?.id?.let { x -> click(x) }
            }

            recyclerWrapper.visibility = if (showLatestRecycler) View.VISIBLE else View.GONE
            if (showLatestRecycler) {
                latestMoviesRecyclerInit(recycler)
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

