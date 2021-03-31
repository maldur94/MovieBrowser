package com.pbartkowiak.moviebrowser.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun <T : ViewModel> getViewModel(viewModelClass: Class<T>) =
        ViewModelProvider(this).get(viewModelClass)
}
