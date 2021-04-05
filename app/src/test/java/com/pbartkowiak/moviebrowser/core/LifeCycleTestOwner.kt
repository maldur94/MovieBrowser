package com.pbartkowiak.moviebrowser.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class LifeCycleTestOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle() = registry

    fun onCreate() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun onDestroy() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}
