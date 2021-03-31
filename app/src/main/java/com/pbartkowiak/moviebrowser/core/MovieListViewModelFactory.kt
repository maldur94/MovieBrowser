package com.pbartkowiak.moviebrowser.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.ui.MovieListViewModel

class MovieListViewModelFactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
