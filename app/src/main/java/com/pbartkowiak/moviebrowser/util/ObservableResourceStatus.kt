package com.pbartkowiak.moviebrowser.util

import androidx.databinding.BaseObservable
import com.pbartkowiak.moviebrowser.core.data.ResourceStatus

class ObservableResourceStatus : BaseObservable {

    private var value: ResourceStatus? = null

    constructor(value: ResourceStatus?) {
        this.value = value
    }

    constructor()

    fun get() = value ?: ""

    fun set(value: ResourceStatus?) {
        if (value == null) {
            this.value = null
        } else if (value != this.value) {
            this.value = value
        }
        notifyChange()
    }
}
