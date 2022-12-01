package com.example.tmdb_test_app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie

class TopRatedMovieAdapter(
    private val config: Config,
    private val click: (movieId: Long) -> Unit,
    private val imageLoader: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit
) :
    PagingDataAdapter<Movie, TopRatedMovieAdapter.PosterViewHolder>(LatestMovieDiffItemCallback) {

    class PosterViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.movie_poster)

        fun bind(
            movie: Movie?,
            click: (movieId: Long) -> Unit,
            imageLoad: (url: String?, imageView: ImageView, width: Int, height: Int) -> Unit,
            config: Config
        ) {
            val url = if (movie == null) null else config.imageUrl + movie.posterPath
            imageLoad(url, poster, 100, 200)
            poster.setOnClickListener {
                movie?.id?.let { x -> click(x) }
            }
        }

        fun cleanup(){
            poster.clear()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_poster_layout, viewGroup, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(getItem(position), click, imageLoader, config)
    }

    override fun onViewRecycled(holder: PosterViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()
    }
}

object LatestMovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
