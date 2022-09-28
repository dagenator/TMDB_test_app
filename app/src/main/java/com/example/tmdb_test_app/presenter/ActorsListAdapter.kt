package com.example.tmdb_test_app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Cast

class ActorsListAdapter(private val dataSet: Array<Cast>) :
    RecyclerView.Adapter<ActorsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val actorName: TextView

        init {
            actorName = view.findViewById(R.id.actorName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.actors_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.actorName.text = dataSet[position].name
    }

    override fun getItemCount() = dataSet.size
}
