package com.pbartkowiak.moviebrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pbartkowiak.moviebrowser.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by activityViewModels()

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
        fun buildFragment() = MovieDetailsFragment()
    }
}
