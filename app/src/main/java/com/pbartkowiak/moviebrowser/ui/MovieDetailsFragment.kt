package com.pbartkowiak.moviebrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pbartkowiak.moviebrowser.core.ui.BaseFragment
import com.pbartkowiak.moviebrowser.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getViewModel(MovieDetailsViewModel::class.java)
        viewModel.setupDetailView(arguments!!.getString(MOVIE_IMAGE_URL_ID_KEY_EXTRA))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MovieDetailsFragment
            return root
        }
    }

    companion object {
        fun buildFragment(imageUrl: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_IMAGE_URL_ID_KEY_EXTRA, imageUrl)
                }
            }
    }
}
