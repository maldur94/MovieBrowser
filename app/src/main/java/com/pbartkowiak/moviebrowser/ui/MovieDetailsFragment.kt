package com.pbartkowiak.moviebrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pbartkowiak.moviebrowser.core.ui.BaseFragment
import com.pbartkowiak.moviebrowser.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : BaseFragment() {

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MovieDetailsViewModel::class.java)
        viewModel.setupDetailView(arguments!!.getString(WEBSITE_URL_ID_KEY_EXTRA))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    companion object {
        fun buildFragment(websiteUrl: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(WEBSITE_URL_ID_KEY_EXTRA, websiteUrl)
                }
            }
    }
}
