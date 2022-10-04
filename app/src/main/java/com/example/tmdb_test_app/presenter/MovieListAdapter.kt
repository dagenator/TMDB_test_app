package com.example.tmdb_test_app.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie


class MovieListAdapter(
    private val dataSet: Array<Movie>,
    private val config: Config
) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieRowPoster: ImageView
        val movieRowName: TextView
        val movieRowScore: TextView
        val movieRowData: TextView

        init {
            movieRowPoster = view.findViewById(R.id.movie_row_poster)
            movieRowName = view.findViewById(R.id.movie_row_name)
            movieRowScore = view.findViewById(R.id.movie_row_score)
            movieRowData = view.findViewById(R.id.movie_row_data)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_row, viewGroup, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageView: ImageView = holder.movieRowPoster
        val currentUrl: String = config.imageUrl + dataSet[position].posterPath

        Glide.with(holder.itemView.context)
            .load(currentUrl)
            .error(R.drawable.no_image)
            .into(imageView)

        holder.movieRowData.text = dataSet[position].releaseDate
        holder.movieRowName.text = dataSet[position].title
        holder.movieRowScore.text = dataSet[position].voteAverage.toString()

    }

    override fun getItemCount() = dataSet.size ?: 0
}
