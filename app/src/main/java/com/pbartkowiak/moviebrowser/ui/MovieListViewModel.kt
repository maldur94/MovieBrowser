package com.pbartkowiak.moviebrowser.ui

import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.core.data.ResourceStatus
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.util.HyperlinkFinder
import com.pbartkowiak.moviebrowser.util.event.EmptyEvent
import com.pbartkowiak.moviebrowser.util.event.Event
import com.pbartkowiak.moviebrowser.util.event.Event2
import timber.log.Timber

class MovieListViewModel(private val resources: Resources, val microService: MovieMicroService) :
    ViewModel(), ItemCallback<Movie> {

    val movieList = ObservableArrayList<Movie>()

    val loadingStatus = ResourceStatus.zip(microService.movies)

    val refreshMovies: LiveData<EmptyEvent>
        get() = mutableRefreshMovies
    val proceedMovieChosen: LiveData<Event<String>>
        get() = mutableProceedMovieChosen
    val showInternetConnectionErrorDialog: MutableLiveData<Event2<Int, String>>
        get() = mutableShowInternetConnectionErrorDialog

    private val mutableRefreshMovies = MutableLiveData<EmptyEvent>()
    private val mutableProceedMovieChosen = MutableLiveData<Event<String>>()
    private val mutableShowInternetConnectionErrorDialog = MutableLiveData<Event2<Int, String>>()

    init {
        if (movieList.isEmpty()) callForMovies()
    }

    override fun onItemClick(item: Movie) {
        mutableProceedMovieChosen.value = Event(HyperlinkFinder.getUrl(item.description))
    }

    fun refreshMovies() {
        mutableRefreshMovies.value = EmptyEvent()
    }

    fun callForMovies() {
        microService.callMovies()
    }

    fun onMoviesDownloaded(movies: Resource<List<Movie>>?) {
        val data = movies?.data
        val message = movies?.message ?: ""
        if (isStatusError(movies?.status)) {
            Timber.e(resources.getString(R.string.connection_error_no_internet))
            showInternetConnectionErrorDialog(message)
        } else if (!data.isNullOrEmpty()) {
            movieList.clear()
            this.movieList.addAll(data.sortedBy { it.orderId })
        }
    }

    private fun isStatusError(status: ResourceStatus?) = status == ResourceStatus.ERROR

    private fun showInternetConnectionErrorDialog(message: String) {
        mutableShowInternetConnectionErrorDialog.value = Event2(
            R.string.connection_error_dialog_title,
            message
        )
    }
}
