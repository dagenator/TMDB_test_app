package com.example.tmdb_test_app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie

class MovieListAdapter(
    private val config: Config,
    private val context: Fragment
) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffItemCallback) {


    class MovieViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        val movieRowPoster: ImageView
        val movieRowName: TextView
        val movieRowScore: TextView
        val movieRowData: TextView
        val movieRow : ConstraintLayout

        init {
            movieRowPoster = view.findViewById(R.id.movie_row_poster)
            movieRowName = view.findViewById(R.id.movie_row_name)
            movieRowScore = view.findViewById(R.id.movie_row_score)
            movieRowData = view.findViewById(R.id.movie_row_data)
            movieRow = view.findViewById(R.id.movie_row)
        }

        fun bind(movie: Movie?, context: Fragment, config: Config) {

            movie?.let {
                val url = config.imageUrl + movie.posterPath
                Glide
                    .with(context)
                    .load(url)
                    .error(R.drawable.no_image)
                    .apply(RequestOptions().override(100, 200))
                    .into(movieRowPoster)
            }

            if (movie == null) {
                Glide
                    .with(context)
                    .load(R.drawable.no_image)
                    .apply(RequestOptions().override(100, 200))
                    .into(movieRowPoster)
            }
            movieRowName.text = movie?.title
            movieRowData.text = movie?.releaseDate
            movieRowScore.text = movie?.voteAverage.toString()
            movieRow.setOnClickListener {
                val navController = view.findNavController()
                val bundle = bundleOf("movieId" to movie?.id)
                navController.navigate(R.id.movieFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row, viewGroup, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), context, config)
    }

}

object MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

