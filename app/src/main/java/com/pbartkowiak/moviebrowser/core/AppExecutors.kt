package com.pbartkowiak.moviebrowser.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object AppExecutors {

    private val diskIo: Executor = Executors.newSingleThreadExecutor()
    private val mainThread: Executor = MainThreadExecutor()

    fun diskIo() = diskIo

    fun mainThread() = mainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
