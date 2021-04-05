package com.pbartkowiak.moviebrowser.util.event

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        this
    }

    fun peek() = content
}
