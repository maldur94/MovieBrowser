package com.pbartkowiak.moviebrowser.ui

import android.content.DialogInterface
import android.content.res.Resources
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.core.ui.BaseViewModel
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.util.ObservableString
import com.pbartkowiak.moviebrowser.util.event.EmptyEvent
import com.pbartkowiak.moviebrowser.util.event.Event5
import timber.log.Timber

private const val UNKNOWN_HOST_EXCEPTION = "UnknownHostException"

class MovieListViewModel : BaseViewModel(), ItemCallback<Movie> {

    private lateinit var movieLiveData: LiveData<Resource<List<Movie>>>

    val movieList = ObservableArrayList<Movie>()
    val errorText = ObservableString()
    val emptyListOrErrorTextVisibility = ObservableInt(View.VISIBLE)

    val movies: LiveData<List<Movie>>
        get() = mutableMovies
    val refreshMovies: LiveData<EmptyEvent>
        get() = mutableRefreshMovies
    val proceedMovieChosen: LiveData<EmptyEvent>
        get() = mutableProceedMovieChosen
    val showInternetConnectionErrorDialog: MutableLiveData<Event5<Int, Int, Int, Int, (DialogInterface, Int) -> Unit>>
        get() = mutableShowInternetConnectionErrorDialog

//    val allMovies = repository.allMovies.asLiveData()

    private val mutableMovies = MutableLiveData<List<Movie>>()
    private val mutableRefreshMovies = MutableLiveData<EmptyEvent>()
    private val mutableProceedMovieChosen = MutableLiveData<EmptyEvent>()
    private val mutableShowInternetConnectionErrorDialog =
        MutableLiveData<Event5<Int, Int, Int, Int, (DialogInterface, Int) -> Unit>>()

    override fun onItemClick(item: Movie) {
        mutableProceedMovieChosen.value = EmptyEvent()
    }

    fun initSwipeRefresh() {
        mutableRefreshMovies.value = EmptyEvent()
    }

    fun onMoviesDownloaded(movies: List<Movie>) {
        emptyListOrErrorTextVisibility.set(if (movies.isEmpty()) View.VISIBLE else View.INVISIBLE)
        movieList.clear()
        this.movieList.addAll(movies.sortedBy { it.orderId })
    }

    fun isInternetConnectionAvailable(resources: Resources, t: Throwable? = null) =
        if (t.toString().contains(UNKNOWN_HOST_EXCEPTION)) {
            Timber.e("Connection failed: $t")
            setErrorMessage(resources)
            showInternetConnectionErrorDialog()
            false
        } else {
            true
        }

    fun callForMovies(resources: Resources) {
//        movieLiveData = MovieRepository.loadAllMovies()
    }

//    private fun addAllMoviesToDao() = viewModelScope.launch {
//        repository.insertAllMovies(movieList)
//    }

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

    private fun setEmptyListMessage(resources: Resources) {
        errorText.set(resources.getString(R.string.activity_movie_browser_empty_list_message))
    }

    private fun setErrorMessage(resources: Resources) {
        errorText.set(resources.getString(R.string.no_internet_connection_error))
    }
}
