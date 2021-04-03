package com.pbartkowiak.moviebrowser.core.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment : Fragment() {

    protected fun <T : ViewModel> getViewModel(viewModelClass: Class<T>) =
        ViewModelProvider(this).get(viewModelClass)
}
