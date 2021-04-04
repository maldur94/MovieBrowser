package com.pbartkowiak.moviebrowser.data.repository

import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.data.BoundResource
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.MovieDao
import com.pbartkowiak.moviebrowser.data.source.MovieService

class MovieRepository(
    private val appExecutors: AppExecutors,
    private val movieService: MovieService,
    private val movieDao: MovieDao
) {

    fun loadAllMovies() = object : BoundResource<List<Movie>, List<Movie>>(appExecutors) {
        override fun saveCallResult(item: List<Movie>) {
            movieDao.insertMovies(item)
        }

        override fun shouldFetch(data: List<Movie>?) = data == null || data.isNullOrEmpty()

        override fun loadFromDb() = movieDao.getAllMovies()

        override fun createCall() = movieService.getAllMovies()
    }.asLiveData()
}
