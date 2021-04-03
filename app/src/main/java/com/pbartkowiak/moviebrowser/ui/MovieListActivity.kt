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
        viewModel = getViewModel(MovieListViewModel::class.java, resources)
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
        val context = this
        val database = DatabaseBuilder.build(this)
        val movieDao = database.moviesDao
        val repository =
            MovieRepository(AppExecutors, NetworkManager(context).provideMovieService(), movieDao)
        viewModel.run {
            binding.swipeRefresh.setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                refreshMovies(repository)
            }
            callForMovies(repository)
            refreshMovies.observe(context, {
                callForMovies(repository)
            })
            movies.observe(context, { movies ->
                onMoviesDownloaded(movies, database)
            })
            proceedMovieChosen.observe(context, { result ->

                if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
                    val fragment = MovieDetailsFragment.buildFragment(result.peek())
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    startActivity(MovieDetailsActivity.buildIntent(context, result.peek()))
                }
            })
            showInternetConnectionErrorDialog.observe(context, {
                it.getContentIfNotHandled()?.let { event ->
                    AlertDialog.Builder(context)
                        .setIcon(ContextCompat.getDrawable(context, event.peek1()))
                        .setTitle(getString(event.peek2()))
                        .setMessage(getString(event.peek3()))
                        .setPositiveButton(getString(event.peek4())) { _: DialogInterface, _: Int ->
                            event.peek5()
                        }
                        .show()
                }
            })
        }
    }
}
