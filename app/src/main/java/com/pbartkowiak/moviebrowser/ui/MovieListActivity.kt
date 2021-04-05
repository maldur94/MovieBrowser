package com.pbartkowiak.moviebrowser.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.NetworkManager
import com.pbartkowiak.moviebrowser.core.data.DatabaseBuilder
import com.pbartkowiak.moviebrowser.core.ui.BaseActivity
import com.pbartkowiak.moviebrowser.data.repository.MovieRepository
import com.pbartkowiak.moviebrowser.databinding.ActivityMovieBrowserListBinding

class MovieListActivity : BaseActivity() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseBuilder.build(this)
        val movieDao = database.moviesDao()
        val repository =
            MovieRepository(AppExecutors, NetworkManager(this).provideMovieService(), movieDao)
        val microService = MovieMicroService(repository)
        viewModel = getViewModel(MovieListViewModel::class.java, resources, microService)
        ActivityMovieBrowserListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieListActivity
            viewModel = this@MovieListActivity.viewModel
            setContentView(root)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }
            attachObservers(this, this@MovieListActivity.viewModel)
        }
    }

    private fun attachObservers(
        binding: ActivityMovieBrowserListBinding,
        viewModel: MovieListViewModel
    ) {
        viewModel.run {
            binding.swipeRefresh.setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                refreshMovies()
            }
            refreshMovies.observe(this@MovieListActivity, {
                callForMovies()
            })
            microService.movies.observe(this@MovieListActivity, { movies ->
                onMoviesDownloaded(movies)
            })
            proceedMovieChosen.observe(this@MovieListActivity, { event ->
                event.getContentIfNotHandled().let {
                    if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
                        val fragment = MovieDetailsFragment.buildFragment(event.peek())
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                    } else {
                        startActivity(
                            MovieDetailsActivity.buildIntent(
                                this@MovieListActivity,
                                event.peek()
                            )
                        )
                    }
                }
            })
            showInternetConnectionErrorDialog.observe(this@MovieListActivity, {
                it.getContentIfNotHandled()?.let { event ->
                    AlertDialog.Builder(this@MovieListActivity)
                        .setIcon(
                            ContextCompat.getDrawable(
                                this@MovieListActivity,
                                R.drawable.ic_fa_exclamation_triangle
                            )
                        )
                        .setTitle(getString(event.peek1()))
                        .setMessage(event.peek2())
                        .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int -> }
                        .show()
                }
            })
        }
    }
}
