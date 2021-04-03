package com.pbartkowiak.moviebrowser.core.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.core.MovieListViewModelFactory

abstract class BaseActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun <T : ViewModel> getViewModel(
        viewModelClass: Class<T>,
        resources: Resources
    ) = ViewModelProvider(this, MovieListViewModelFactory(resources)).get(viewModelClass)

    protected fun <T : ViewModel> getViewModel(
        viewModelClass: Class<T>
    ) = ViewModelProvider(this).get(viewModelClass)
}
