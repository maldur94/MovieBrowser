package com.pbartkowiak.moviebrowser.ui.adapter

import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.util.ObservableString

class MovieItemViewModel(private var callback: ItemCallback<Movie>) {

    val title = ObservableString()
    val description = ObservableString()
    val modificationDate = ObservableString()
    val imageUrl = ObservableString()

    var movie: Movie? = null

    fun onMovieClick() {
        movie?.run { callback.onItemClick(this) }
    }

    fun bind(movie: Movie) {
        this.movie = movie
        title.set(movie.title)
        description.set(movie.description)
        modificationDate.set(movie.modificationDate)
        imageUrl.set(movie.imageUrl)
    }
}
