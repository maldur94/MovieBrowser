package com.pbartkowiak.moviebrowser.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DefaultLocale")
fun String.toDateFormat(): String {
    val locale = Locale.ROOT
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", locale)
    val date = LocalDate.parse(this, formatter)
    return "${date.month.toString().toLowerCase(locale).capitalize(locale)}/${date.year}"
}
