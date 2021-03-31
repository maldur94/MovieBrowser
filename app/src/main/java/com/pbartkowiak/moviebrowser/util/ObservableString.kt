package com.pbartkowiak.moviebrowser.util

import androidx.databinding.BaseObservable

class ObservableString : BaseObservable {

    private var value: String? = null

    constructor(value: String?) {
        this.value = value
    }

    constructor()

    fun get() = value ?: ""

    fun set(value: String?) {
        if (value == null) {
            this.value = null
        } else if (value != this.value) {
            this.value = value
        }
        notifyChange()
    }
}
