@file:Suppress("UNCHECKED_CAST")

package com.pbartkowiak.moviebrowser.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

object VerifyUtils {
    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T : Any> safeEq(value: T): T = eq(value)

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    fun <T> verify(liveData: LiveData<T>, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(any())
    }

    fun <T> verify(liveData: LiveData<T>, t: T, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(t)
    }

    fun <T> verify(liveData: LiveData<T>, captor: ArgumentCaptor<T>, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(captor.capture())
    }

    fun <T> verify(liveData: LiveData<T>, captor: KArgumentCaptor<T>, times: Int = 1) {
        val observer: Observer<T> = mock()
        liveData.observeForever(observer)
        verify(observer, times(times)).onChanged(captor.capture())
    }

    private fun <T> uninitialized(): T = null as T
}