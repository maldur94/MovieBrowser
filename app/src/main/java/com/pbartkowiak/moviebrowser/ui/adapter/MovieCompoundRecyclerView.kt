package com.pbartkowiak.moviebrowser.ui.adapter

import android.content.Context
import android.util.AttributeSet
import com.pbartkowiak.moviebrowser.core.ItemCallback
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.util.CompoundRecyclerView

class MovieCompoundRecyclerView(context: Context, attrs: AttributeSet) :
    CompoundRecyclerView<Movie>(context, attrs) {

    private val adapter = MovieListAdapter()

    init {
        init(adapter)
    }

    override fun updateResults(list: List<Movie>) {
        adapter.updateItems(list)
    }

    override fun setRecyclerViewCallback(callback: ItemCallback<Movie>) {
        adapter.callback = callback
    }
}
