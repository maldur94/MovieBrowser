package com.pbartkowiak.moviebrowser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.util.event.EmptyEvent

class MovieMicroService(private val movieRepository: MovieRepository) {

    private val mutableMovies = MutableLiveData<EmptyEvent>()

    val movies: LiveData<Resource<List<Movie>>> =
        mutableMovies.switchMap {
            movieRepository.loadAllMovies()
        }

    internal fun callMovies() {
        mutableMovies.value = EmptyEvent()
    }
}
