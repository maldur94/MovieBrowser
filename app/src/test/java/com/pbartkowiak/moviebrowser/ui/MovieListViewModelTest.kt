package com.pbartkowiak.moviebrowser.ui

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.BaseViewModelTest
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.util.event.Event
import com.pbartkowiak.moviebrowser.util.event.Event2
import com.pbartkowiak.moviebrowser.utils.VerifyUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MovieListViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var resources: Resources
    private lateinit var microService: MovieMicroService
    private lateinit var movieRepository: MovieRepository

    @Before
    override fun setup() {
        super.setup()
        resources = mock()
        movieRepository = mock()
        microService = MovieMicroService(movieRepository)
        viewModel = MovieListViewModel(resources, microService)
    }

    @Test
    fun `When onItemClick is called proceedMovieChosen is proceeded with extracted hyperlinc from movie description`() {
        // given
        val item = Movie(
            orderId = 0,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val linkExpected = "http://testurl.com"

        // when
        viewModel.onItemClick(item)

        // then
        val captor = argumentCaptor<Event<String>>()
        VerifyUtils.verify(viewModel.proceedMovieChosen, captor)
        assertEquals(linkExpected, viewModel.proceedMovieChosen.value!!.peek())
    }

    @Test
    fun `When refreshMovies is called movies is filled up with proper movie data`() {
        // given
        val item = Movie(
            orderId = 0,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val moviesExpected = MutableLiveData(Resource.success(listOf(item)))
        Mockito.`when`(movieRepository.loadAllMovies()).thenReturn(moviesExpected)

        // when
        viewModel.refreshMovies()

        // then
        VerifyUtils.verify(viewModel.microService.movies)
        assertEquals(moviesExpected.value, viewModel.microService.movies.value)
    }

    @Test
    fun `When callForMovies is called movies is filled up with proper movie data`() {
        // given
        val item = Movie(
            orderId = 0,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val moviesExpected = MutableLiveData(Resource.success(listOf(item)))
        Mockito.`when`(movieRepository.loadAllMovies()).thenReturn(moviesExpected)

        // when
        viewModel.callForMovies()

        // then
        VerifyUtils.verify(viewModel.microService.movies)
        assertEquals(moviesExpected.value, viewModel.microService.movies.value)
    }

    @Test
    fun `When onMoviesDownloaded is called with non empty success resource status, movieList is updated`() {
        // given
        val item1 = Movie(
            orderId = 2,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val item2 = Movie(
            orderId = 4,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val item3 = Movie(
            orderId = 3,
            title = "Movie Title",
            modificationDate = "2004-02-12",
            description = "Movie description http://testurl.com",
            image_url = "http://imageurl.com"
        )
        val moviesExpected = listOf(item1, item3, item2)
        val movieResource = Resource.success(listOf(item1, item2, item3))

        // when
        viewModel.onMoviesDownloaded(movieResource)

        // then
        assertEquals(moviesExpected, viewModel.movieList)
    }

    @Test
    fun `When onMoviesDownloaded is called with error resource status, proper error message is shown`() {
        // given
        val messageExpected = "Error message"
        val titleExpected = R.string.connection_error_dialog_title
        val movieResource = Resource.error(messageExpected, emptyList<Movie>())

        // when
        viewModel.onMoviesDownloaded(movieResource)

        // then
        val captor = argumentCaptor<Event2<Int, String>>()
        VerifyUtils.verify(viewModel.showInternetConnectionErrorDialog, captor)
        assertEquals(titleExpected, captor.firstValue.peek1())
        assertEquals(messageExpected, captor.firstValue.peek2())
    }
}
