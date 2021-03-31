package com.pbartkowiak.moviebrowser.core.data.remote

data class PageDto<T>(
    val totalElements: Long,
    val pageElements: Long,
    val content: List<T>
)
