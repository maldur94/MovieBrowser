package com.pbartkowiak.moviebrowser.util

import androidx.lifecycle.MutableLiveData

/**
 * Swaps content of the [MutableLiveData] with a new [NullableLiveDataValue] if its contents is different.
 */
fun <E> MutableLiveData<NullableLiveDataValue<E>>.swapIfDifferent(newValue: E?) {
    if (newValue == value?.value)
        return

    value = NullableLiveDataValue(newValue)
}

fun <E> MutableLiveData<E>.reload() {
    this.postValue(value)
}

fun <E> MutableLiveData<NullableLiveDataValue<E>>.swapIfDifferentAsync(newValue: E?) {
    if (newValue == value?.value)
        return

    postValue(NullableLiveDataValue(newValue))
}

class MutableLiveDataFactory {
    companion object {
        fun <E> of(value: E?): MutableLiveData<E> {
            return MutableLiveData<E>().also {
                it.value = value
            }
        }
    }
}
