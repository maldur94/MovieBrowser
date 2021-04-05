package com.pbartkowiak.moviebrowser.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Before
    open fun setup() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }
}
