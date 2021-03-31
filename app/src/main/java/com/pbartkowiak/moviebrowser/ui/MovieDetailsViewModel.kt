package com.pbartkowiak.moviebrowser.ui

import com.pbartkowiak.moviebrowser.core.ui.BaseViewModel
import com.pbartkowiak.moviebrowser.util.ObservableString


class MovieDetailsViewModel : BaseViewModel() {

    val title = ObservableString()
    val modificationDate = ObservableString()
    val description = ObservableString()
    val imageUrl = ObservableString()

    fun setupDetailView(
        title: String,
        modificationDate: String,
        description: String,
        imageUrl: String
    ) {
        this.title.set(title)
        this.modificationDate.set(modificationDate)
        this.description.set(description)
        this.imageUrl.set(imageUrl)
    }
}
