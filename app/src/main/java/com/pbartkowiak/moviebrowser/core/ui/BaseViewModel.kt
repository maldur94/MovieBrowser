package com.pbartkowiak.moviebrowser.core.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun <T> fetchFromRemote(
        liveData: MutableLiveData<T>,
        apiSource: suspend () -> T
    ) = viewModelScope.launch { liveData.postValue(apiSource()) }
}
