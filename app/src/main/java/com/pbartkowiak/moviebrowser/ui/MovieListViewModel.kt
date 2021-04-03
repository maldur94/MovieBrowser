package com.pbartkowiak.moviebrowser.ui

import android.content.DialogInterface
import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.core.data.ResourceStatus
import com.pbartkowiak.moviebrowser.core.ui.BaseViewModel
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.data.source.AppDatabase
import com.pbartkowiak.moviebrowser.util.HyperlinkFinder
import com.pbartkowiak.moviebrowser.util.ObservableResourceStatus
import com.pbartkowiak.moviebrowser.util.ObservableString
import com.pbartkowiak.moviebrowser.util.event.EmptyEvent
import com.pbartkowiak.moviebrowser.util.event.Event
import com.pbartkowiak.moviebrowser.util.event.Event5
import timber.log.Timber

private const val UNKNOWN_HOST_EXCEPTION = "UnknownHostException"

class MovieListViewModel(private val resources: Resources) : BaseViewModel(), ItemCallback<Movie> {

    val movieList = ObservableArrayList<Movie>()
    val textError =
        ObservableString(resources.getString(R.string.activity_movie_browser_empty_list_message))

    val loadingStatus = ObservableResourceStatus()
    val movies: LiveData<Resource<List<Movie>>>
        get() = mutableMovies
    val refreshMovies: LiveData<EmptyEvent>
        get() = mutableRefreshMovies
    val proceedMovieChosen: LiveData<Event<String>>
        get() = mutableProceedMovieChosen
    val showInternetConnectionErrorDialog: MutableLiveData<Event5<Int, Int, Int, Int, (DialogInterface, Int) -> Unit>>
        get() = mutableShowInternetConnectionErrorDialog

    private val mutableMovies = MutableLiveData<Resource<List<Movie>>>(Resource.success(null))
    private val mutableRefreshMovies = MutableLiveData<EmptyEvent>()
    private val mutableProceedMovieChosen = MutableLiveData<Event<String>>()
    private val mutableShowInternetConnectionErrorDialog =
        MutableLiveData<Event5<Int, Int, Int, Int, (DialogInterface, Int) -> Unit>>()

    override fun onItemClick(item: Movie) {
        mutableProceedMovieChosen.value = Event(HyperlinkFinder.getUrl(item.description))
    }

    fun refreshMovies(repository: MovieRepository) {
        callForMovies(repository)
    }

    fun onMoviesDownloaded(movies: Resource<List<Movie>>?, database: AppDatabase) {
        val data = movies?.data
        val message = movies?.message ?: ""
        if (isInternetConnectionUnavailable(message)) {
            textError.set(resources.getString(R.string.no_internet_connection_error))
            Timber.e(resources.getString(R.string.no_internet_connection_error))
            showInternetConnectionErrorDialog()
        } else if (!data.isNullOrEmpty()) {
            database.moviesDao
            movieList.clear()
            this.movieList.addAll(data.sortedBy { it.orderId })
        }
    }

    fun callForMovies(repository: MovieRepository) {
//        val callback: ApiCallback =
//            object : ApiCallback {
//                override fun onResult(result: Resource<List<Movie>>) {
//                    loadingStatus.set(result.status)
//                    mutableMovies.value = result
//                }
//            }
        loadingStatus.set(ResourceStatus.LOADING)
        repository.loadAllMovies()
    }

    private fun isInternetConnectionUnavailable(errorMessage: String) =
        errorMessage.contains(UNKNOWN_HOST_EXCEPTION)

    private fun showInternetConnectionErrorDialog() {
        mutableShowInternetConnectionErrorDialog.value = Event5(
            R.drawable.ic_fa_exclamation_triangle,
            R.string.no_internet_connection_dialog_title,
            R.string.no_internet_connection_dialog_message,
            R.string.no_internet_connection_dialog_button_text,
            { dialogInterface: DialogInterface, _ ->
                dialogInterface.dismiss()
            })
    }
}

interface ApiCallback {
    fun onResult(result: Resource<List<Movie>>)
}
