package com.pbartkowiak.moviebrowser.ui

import androidx.lifecycle.ViewModel
import com.pbartkowiak.moviebrowser.util.ObservableString


class MovieDetailsViewModel : ViewModel() {

    val urlAddress = ObservableString()

    fun setupDetailView(urlAddress: String?) {
        this.urlAddress.set(urlAddress)
    }
}
