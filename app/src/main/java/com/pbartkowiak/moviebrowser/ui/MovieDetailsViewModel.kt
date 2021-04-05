package com.pbartkowiak.moviebrowser.ui

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.pbartkowiak.moviebrowser.util.ObservableString


class MovieDetailsViewModel : ViewModel() {

    val websiteUrl = ObservableString()
    val loadingStatus = ObservableBoolean()

    fun setupDetailView(websiteUrl: String?) {
        this.websiteUrl.set(websiteUrl)
    }
}
