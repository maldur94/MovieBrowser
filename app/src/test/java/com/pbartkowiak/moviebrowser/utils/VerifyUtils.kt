@file:Suppress("UNCHECKED_CAST")

package com.pbartkowiak.moviebrowser.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.mockito.Mockito

object VerifyUtils {

    fun <T> verify(liveData: LiveData<T>, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(any())
    }

    fun <T> verify(liveData: LiveData<T>, captor: KArgumentCaptor<T>, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(captor.capture())
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    private fun <T> uninitialized(): T = null as T
}