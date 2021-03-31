package com.pbartkowiak.moviebrowser

import android.app.Application
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.NetworkManager
import com.pbartkowiak.moviebrowser.core.data.DatabaseProvider
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.data.source.MovieService

class MovieBrowserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = DatabaseProvider.provideDatabaseManager(this)
        val appExecutors = AppExecutors
        val networkManager = NetworkManager(this)
        val movieService = networkManager.retrofit().create(MovieService::class.java)
        val movieDao = database.provideMoviesDao()
        MovieRepository.init(appExecutors, movieService, movieDao)
    }
}
