package com.pbartkowiak.moviebrowser.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.util.event.EmptyEvent

class MovieMicroService(private val movieRepository: MovieRepository) {

    private val mutableMovies = MutableLiveData<EmptyEvent>()

    val movies = mutableMovies.switchMap {
        movieRepository.loadAllMovies()
    }

    internal fun callMovies() {
        mutableMovies.value = EmptyEvent()
    }
}
