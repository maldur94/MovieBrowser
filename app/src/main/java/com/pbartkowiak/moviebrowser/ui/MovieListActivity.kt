package com.pbartkowiak.moviebrowser.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ui.BaseActivity
import com.pbartkowiak.moviebrowser.databinding.ActivityMovieBrowserListBinding

class MovieListActivity : BaseActivity() {

    private lateinit var listViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = getViewModel(MovieListViewModel::class.java)
        ActivityMovieBrowserListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieListActivity
            viewModel = this@MovieListActivity.listViewModel
            setContentView(root)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }
            attachObservers(this, this@MovieListActivity.listViewModel)
        }
    }

    private fun attachObservers(
        binding: ActivityMovieBrowserListBinding,
        listViewModel: MovieListViewModel
    ) {
        listViewModel.run {
            binding.swipeRefresh.setOnRefreshListener {
                initSwipeRefresh()
            }
            callForMovies(resources)
            refreshMovies.observe(this@MovieListActivity, {
                callForMovies(resources)
            })
            movies.observe(this@MovieListActivity, {
                onMoviesDownloaded(it)
                binding.swipeRefresh.isRefreshing = false
            })
            proceedMovieChosen.observe(this@MovieListActivity, {
                startActivity(
                    Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
                )
            })
            showInternetConnectionErrorDialog.observe(this@MovieListActivity, {
                it.getContentIfNotHandled()?.let { event ->
                    AlertDialog.Builder(this@MovieListActivity)
                        .setIcon(
                            ContextCompat.getDrawable(
                                this@MovieListActivity, event.peek1()
                            )
                        )
                        .setTitle(this@MovieListActivity.getString(event.peek2()))
                        .setMessage(this@MovieListActivity.getString(event.peek3()))
                        .setPositiveButton(
                            this@MovieListActivity.getString(event.peek4())
                        ) { _: DialogInterface, _: Int ->
                            event.peek5()
                        }
                        .show()
                }
            })
        }
    }
}
