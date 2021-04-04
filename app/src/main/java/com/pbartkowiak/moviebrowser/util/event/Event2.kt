package com.pbartkowiak.moviebrowser.util.event

class Event2<out T, out D>(private val content: T, private val content2: D) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        this
    }

    fun peek1() = content

    fun peek2() = content2
}
