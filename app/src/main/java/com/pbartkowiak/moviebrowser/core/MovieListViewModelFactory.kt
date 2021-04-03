package com.pbartkowiak.moviebrowser.core

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.ui.MovieListViewModel

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(private val resources: Resources) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            MovieListViewModel(resources) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
