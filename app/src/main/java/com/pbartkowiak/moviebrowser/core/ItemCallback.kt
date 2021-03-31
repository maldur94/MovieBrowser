package com.pbartkowiak.moviebrowser.core

interface ItemCallback<T> {
    fun onItemClick(item: T)
}
