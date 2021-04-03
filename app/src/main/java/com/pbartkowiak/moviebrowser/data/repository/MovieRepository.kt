package com.pbartkowiak.moviebrowser.data.repository

import androidx.lifecycle.LiveData
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.data.BoundResource
import com.pbartkowiak.moviebrowser.core.data.Resource
import com.pbartkowiak.moviebrowser.core.data.remote.ApiResponse
import com.pbartkowiak.moviebrowser.core.data.remote.PageDto
import com.pbartkowiak.moviebrowser.data.model.Movie
import com.pbartkowiak.moviebrowser.data.source.MovieDao
import com.pbartkowiak.moviebrowser.data.source.MovieDto
import com.pbartkowiak.moviebrowser.data.source.MovieService
import com.pbartkowiak.moviebrowser.data.source.toEntity
import com.pbartkowiak.moviebrowser.ui.ApiCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

class MovieRepository(private val appExecutors: AppExecutors, private val movieService: MovieService, private val movieDao: MovieDao) {

    fun loadAllMovies() =
        loadAll(
            dbCallProvider = { movieDao.getAllMovies() },
            apiCallProvider = { movieService.getAllMovies() }
        )

    private fun loadAll(
        dbCallProvider: () -> LiveData<List<Movie>>,
        apiCallProvider: () -> LiveData<ApiResponse<PageDto<MovieDto>>>
    ) =
        object : BoundResource<List<Movie>, PageDto<MovieDto>>(appExecutors) {

            override fun loadFromDb() = dbCallProvider()

            override fun createFetchCall() = apiCallProvider()

            override fun saveFetchCallResult(
                fetchData: PageDto<MovieDto>,
                oldDbData: List<Movie>?
            ) = syncDbData(movieDao, oldDbData, mapFetchCallResult(fetchData))

            private fun mapFetchCallResult(fetchData: PageDto<MovieDto>) =
                fetchData.content.map { it.toEntity() }

        }.asLiveData()

//    fun loadAllMovies(callback: ApiCallback) {
//        movieService.getAllMovies().enqueue(object : Callback<List<MovieDto>> {
//            override fun onResponse(
//                call: Call<List<MovieDto>>,
//                response: Response<List<MovieDto>>
//            ) {
//                if (response.isSuccessful) {
//                    BoundResource.syncDbData(movieDao, )
//
//                    val data = response.body()?.map { it.toEntity() }
//                    data?.forEach { movieDao.insert(it) }
//                    Timber.e("SUCCESS: ${response.body()}")
//                    callback.onResult(Resource.success(data))
//                }
//            }
//
//            override fun onFailure(call: Call<List<MovieDto>>, t: Throwable) {
//                Timber.e("ERROR: $t")
//                val oldData = movieDao.getAllMovies()
//                if (!oldData.value.isNullOrEmpty()) {
//                    callback.onResult(Resource.error(message = t.toString(), data = oldData.value))
//                } else {
//                    callback.onResult(Resource.error(message = t.toString(), data = null))
//                }
//            }
//        })
//    }
}
