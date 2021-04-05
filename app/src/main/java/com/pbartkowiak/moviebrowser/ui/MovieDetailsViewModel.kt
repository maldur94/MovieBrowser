package com.pbartkowiak.moviebrowser.ui

import androidx.lifecycle.ViewModel
import com.pbartkowiak.moviebrowser.util.ObservableString

class MovieDetailsViewModel : ViewModel() {

    val websiteUrl = ObservableString()

    fun setupDetailView(websiteUrl: String?) {
        this.websiteUrl.set(websiteUrl)
    }
}
