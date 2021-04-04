package com.pbartkowiak.moviebrowser.util.event

class Event<out T>(private val content: T) {

    fun peek() = content
}
