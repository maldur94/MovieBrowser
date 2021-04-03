package com.pbartkowiak.moviebrowser.ui

import com.pbartkowiak.moviebrowser.core.ui.BaseViewModel
import com.pbartkowiak.moviebrowser.util.ObservableString


class MovieDetailsViewModel : BaseViewModel() {

    val urlAddress = ObservableString()

    fun setupDetailView(urlAddress: String?) {
        this.urlAddress.set(urlAddress)
    }
}
