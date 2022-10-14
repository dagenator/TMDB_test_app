package com.example.tmdb_test_app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.DBMovie

class FavouriteListAdapter(private val dataSet: Array<DBMovie>, private val click: (movieId: Long) -> Unit) :
    RecyclerView.Adapter<FavouriteListAdapter.FavouriteMovieViewHolder>() {

    class FavouriteMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val year: TextView
        val genre: TextView
        val score: TextView
        val row: ConstraintLayout

        init {
            name = view.findViewById(R.id.favourite_row_name)
            year = view.findViewById(R.id.favourite_row_year)
            genre = view.findViewById(R.id.favourite_row_genre)
            score = view.findViewById(R.id.favourite_row_score)
            row = view.findViewById(R.id.favourite_row)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.favourite_movie_row, viewGroup, false)

        return FavouriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(view: FavouriteMovieViewHolder, position: Int) {
        view.name.text = dataSet[position].title
        view.genre.text = dataSet[position].genres
        view.year.text = dataSet[position].releaseDate.split("-")[0]
        view.score.text = dataSet[position].voteAverage.toString()

        view.row.setOnClickListener {
            click(dataSet[position].id)
//            val navController = context.findNavController()
//            val bundle = bundleOf("movieId" to dataSet[position].id)
//            navController.navigate(R.id.movieFragment, bundle)
        }
    }

    override fun getItemCount() = dataSet.size
}
