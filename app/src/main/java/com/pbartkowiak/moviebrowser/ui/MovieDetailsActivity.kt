package com.pbartkowiak.moviebrowser.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.ui.BaseActivity
import com.pbartkowiak.moviebrowser.core.MovieListViewModelFactory
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.data.source.MovieDao
import com.pbartkowiak.moviebrowser.data.source.MovieService
import com.pbartkowiak.moviebrowser.databinding.ActivityMovieBrowserListBinding

class MovieDetailsActivity : BaseActivity() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMovieBrowserListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
            setContentView(root)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }
        }
    }
}
