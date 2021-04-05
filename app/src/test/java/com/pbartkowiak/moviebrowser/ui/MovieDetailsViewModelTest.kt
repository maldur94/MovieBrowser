package com.pbartkowiak.moviebrowser.ui

import com.pbartkowiak.moviebrowser.core.BaseViewModelTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MovieDetailsViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = MovieDetailsViewModel()
    }

    @Test
    fun `When setupDetailView is called urlAddress is filled up with proper url`() {
        // given
        val urlExpected = "http://testurl.com"

        // when
        viewModel.setupDetailView(urlExpected)

        // then
        assertEquals(urlExpected, viewModel.websiteUrl.get())
    }
}
