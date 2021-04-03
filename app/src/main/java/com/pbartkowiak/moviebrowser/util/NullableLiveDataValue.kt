package com.pbartkowiak.moviebrowser.util

import androidx.lifecycle.LiveData

/**
 * Utility class to safely handle null value in the [LiveData] object.
 */
data class NullableLiveDataValue<E>(val value: E?)

/**
 * Checks whether this [LiveData] or the value [E] is null and maps it to [T] using the given function.
 */
inline fun <E, T> NullableLiveDataValue<E>?.ifExists(f: (E) -> LiveData<T>): LiveData<T> {
    return when {
        this != null && this.value != null -> f(value)
        else -> AbsentLiveData.create()
    }
}
