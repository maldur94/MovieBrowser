package com.pbartkowiak.moviebrowser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ui.BaseActivity
import com.pbartkowiak.moviebrowser.databinding.ActivityMovieDetailsBinding

const val MOVIE_IMAGE_URL_ID_KEY_EXTRA = "movie_image_url_id_key_extra"

class MovieDetailsActivity : BaseActivity() {

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MovieDetailsViewModel::class.java)
        ActivityMovieDetailsBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
            setContentView(root)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }
            this@MovieDetailsActivity.viewModel.setupDetailView(
                intent.extras!!.getString(MOVIE_IMAGE_URL_ID_KEY_EXTRA)
            )
        }

//        if (savedInstanceState == null) {
//            val fragment = MovieDetailsFragment.buildFragment(viewModel.imageUrl.get())
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.item_detail_container, fragment)
//                .commit()
//        }
    }

    companion object {
        fun buildIntent(context: Context, imageUrl: String) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putString(MOVIE_IMAGE_URL_ID_KEY_EXTRA, imageUrl)
                putExtras(bundle)
            }
    }
}
