package com.pbartkowiak.moviebrowser.util

import java.util.regex.Pattern

object HyperlinkFinder {

    private val urlPattern: Pattern = Pattern.compile(
        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )

    fun getUrl(s: String): String {
        val urlMatcher = urlPattern.matcher(s)
        var matchStart: Int
        var matchEnd: Int
        var url = ""
        while (urlMatcher.find()) {
            matchStart = urlMatcher.start(1)
            matchEnd = urlMatcher.end()
            url = s.substring(matchStart, matchEnd)
        }
        return url
    }
}
