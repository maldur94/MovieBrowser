package com.pbartkowiak.moviebrowser.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A LiveData class that reloads downloaded data.
 */
class RefreshableLiveData<T : Any?> constructor(val liveDataProvider: () -> LiveData<T>) :
    MediatorLiveData<T>() {

    private lateinit var source: LiveData<T>

    init {
        addNewSource()
    }

    fun refresh() {
        removeSource(source)
        addNewSource()
    }

    private fun addNewSource() {
        source = liveDataProvider()
        addSource(source) { value = it }
    }
}
