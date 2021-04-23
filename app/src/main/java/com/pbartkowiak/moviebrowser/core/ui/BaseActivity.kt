package com.pbartkowiak.moviebrowser.core.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.core.MovieListViewModelFactory
import com.pbartkowiak.moviebrowser.ui.MovieMicroService

abstract class BaseActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun <T : ViewModel> getViewModel(
        viewModelClass: Class<T>,
        resources: Resources,
        microService: MovieMicroService
    ) = ViewModelProvider(this, MovieListViewModelFactory(resources, microService))
        .get(viewModelClass)
}
