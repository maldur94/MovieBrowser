package com.pbartkowiak.moviebrowser.util

import androidx.recyclerview.widget.RecyclerView
import com.pbartkowiak.moviebrowser.core.HasItemId
import com.pbartkowiak.moviebrowser.core.ItemCallback

abstract class BaseListAdapter<T : HasItemId<Int>, RV : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RV>() {

    lateinit var callback: ItemCallback<T>

    var items = listOf<T>()

    override fun getItemCount() = items.size

    fun updateItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }
}
