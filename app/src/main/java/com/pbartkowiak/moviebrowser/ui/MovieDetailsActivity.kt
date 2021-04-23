package com.pbartkowiak.moviebrowser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ui.BaseActivity
import com.pbartkowiak.moviebrowser.databinding.ActivityMovieDetailsBinding

const val WEBSITE_URL_ID_KEY_EXTRA = "website_url_id_key_extra"

class MovieDetailsActivity : BaseActivity() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupDetailView(intent.extras!!.getString(WEBSITE_URL_ID_KEY_EXTRA))
        ActivityMovieDetailsBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
            setContentView(root)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }
        }
        attachFragment(savedInstanceState)
    }

    private fun attachFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, MovieDetailsFragment.buildFragment())
                .commit()
        }
    }

    companion object {
        fun buildIntent(context: Context, websiteUrl: String) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putString(WEBSITE_URL_ID_KEY_EXTRA, websiteUrl)
                putExtras(bundle)
            }
    }
}
