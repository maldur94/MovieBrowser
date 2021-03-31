package com.pbartkowiak.moviebrowser.data.source

import androidx.lifecycle.LiveData
import com.pbartkowiak.moviebrowser.core.API_DATA_SIZE
import com.pbartkowiak.moviebrowser.core.API_PAGE
import com.pbartkowiak.moviebrowser.core.data.remote.ApiResponse
import com.pbartkowiak.moviebrowser.core.data.remote.PageDto
import com.pbartkowiak.moviebrowser.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/recruitment-task")
    fun getAllMovies(
        @Query("page") page: Long = API_PAGE,
        @Query("size") size: Long = API_DATA_SIZE
    ): LiveData<ApiResponse<PageDto<Movie>>>
}
