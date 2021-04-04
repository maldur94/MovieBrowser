package com.pbartkowiak.moviebrowser.core

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.ui.MovieListViewModel
import com.pbartkowiak.moviebrowser.ui.MovieMicroService

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(
    private val resources: Resources,
    private val microService: MovieMicroService
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            MovieListViewModel(resources, microService) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
