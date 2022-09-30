package com.example.tmdb_test_app.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Cast
import com.example.tmdb_test_app.data.models.Movie
import com.example.tmdb_test_app.presenter.utils.ImageUtils
import javax.inject.Inject

class MovieListAdapter @Inject constructor(val imageUtils: ImageUtils, val context: Context) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var dataSet: Array<Movie>? = null

    fun setData(data: Array<Movie>){
        dataSet = data
    }

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
        dataSet?.let {
            imageUtils.loadImage(holder.movieRowPoster, it[position].posterPath, context)
            holder.movieRowData.text =  it[position].releaseDate
            holder.movieRowName.text = it[position].title
            holder.movieRowScore.text = it[position].voteAverage.toString()
        }
    }
    override fun getItemCount() = dataSet?.size ?: 0
}
