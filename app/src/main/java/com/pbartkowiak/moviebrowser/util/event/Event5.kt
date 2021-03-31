package com.pbartkowiak.moviebrowser.util.event

class Event5<out T, out D, out Z, out Y, out X>(
    private val content: T,
    private val content2: D,
    private val content3: Z,
    private val content4: Y,
    private val content5: X
) {

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

    fun peek3() = content3

    fun peek4() = content4

    fun peek5() = content5
}
