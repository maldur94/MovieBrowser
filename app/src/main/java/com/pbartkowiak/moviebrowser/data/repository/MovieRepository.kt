package com.pbartkowiak.moviebrowser.data.repository

import androidx.lifecycle.LiveData
import com.pbartkowiak.moviebrowser.core.API_DATA_SIZE
import com.pbartkowiak.moviebrowser.core.API_PAGE
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.data.BoundResource
import com.pbartkowiak.moviebrowser.core.data.remote.ApiResponse
import com.pbartkowiak.moviebrowser.core.data.remote.PageDto
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.source.MovieDao
import com.pbartkowiak.moviebrowser.data.source.MovieService

object MovieRepository {
    private lateinit var appExecutors: AppExecutors
    private lateinit var movieService: MovieService
    private lateinit var movieDao: MovieDao

    fun init(appExecutors: AppExecutors, movieService: MovieService, movieDao: MovieDao) {
        this.appExecutors = appExecutors
        this.movieService = movieService
        this.movieDao = movieDao
    }

    fun loadAllMovies() =
        loadAll(
            dbCallProvider = { movieDao.getAllMovies() },
            apiCallProvider = { movieService.getAllMovies(API_PAGE, API_DATA_SIZE) }
        )

    private fun loadAll(
        dbCallProvider: () -> LiveData<List<Movie>>,
        apiCallProvider: () -> LiveData<ApiResponse<PageDto<Movie>>>
    ) = object : BoundResource<List<Movie>, PageDto<Movie>>(appExecutors) {

        override fun loadFromDb() = dbCallProvider()

        override fun createFetchCall() = apiCallProvider()

        override fun saveFetchCallResult(fetchData: PageDto<Movie>, oldDbData: List<Movie>?) =
            syncDbData(movieDao, oldDbData, mapFetchCallResult(fetchData))

        private fun mapFetchCallResult(fetchData: PageDto<Movie>) = fetchData.content
    }.asLiveData()
}
