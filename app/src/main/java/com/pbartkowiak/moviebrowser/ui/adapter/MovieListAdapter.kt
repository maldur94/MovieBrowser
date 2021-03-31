package com.pbartkowiak.moviebrowser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.databinding.ItemMovieBinding
import com.pbartkowiak.moviebrowser.util.BaseListAdapter

class MovieListAdapter : BaseListAdapter<Movie, MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MovieViewHolder {
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).run { return MovieViewHolder(this) }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position], callback)
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(operation: Movie, callback: ItemCallback<Movie>) {
        MovieItemViewModel(callback).run {
            binding.viewModel = this
            this.bind(operation)
        }
    }
}
