package com.pbartkowiak.moviebrowser.util.event

class EmptyEvent {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        ""
    }
}
