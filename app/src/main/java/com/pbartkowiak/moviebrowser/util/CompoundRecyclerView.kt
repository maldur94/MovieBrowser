package com.pbartkowiak.moviebrowser.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.core.ItemCallback
import kotlinx.android.synthetic.main.view_list.view.*

abstract class CompoundRecyclerView<T>(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private lateinit var recyclerView: RecyclerView

    abstract fun updateResults(list: List<T>)
    abstract fun setRecyclerViewCallback(callback: ItemCallback<T>)

    protected fun init(adapter: BaseListAdapter<*, *>, addDivider: Boolean = true) {
        recyclerView = View.inflate(context, R.layout.view_list, this).recyclerView
            .apply {
                this@apply.adapter = adapter
                if (addDivider) {
                    addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                }
            }
    }
}
