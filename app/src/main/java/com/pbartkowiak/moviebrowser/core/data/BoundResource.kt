package com.pbartkowiak.moviebrowser.core.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.pbartkowiak.moviebrowser.core.AppExecutors
import com.pbartkowiak.moviebrowser.core.data.local.BaseDao
import com.pbartkowiak.moviebrowser.core.data.remote.ApiEmptyResponse
import com.pbartkowiak.moviebrowser.core.data.remote.ApiErrorResponse
import com.pbartkowiak.moviebrowser.core.data.remote.ApiResponse
import com.pbartkowiak.moviebrowser.core.data.remote.ApiSuccessResponse

/**
 * A generic class that can provide a resource backed by both the database and the API.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class BoundResource<ResultType, RequestType> @MainThread constructor(
    private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading()

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource) { dbData ->
            result.removeSource(dbSource)
            setValue(Resource.loading(dbData))

            if (shouldFetch(dbData)) {
                fetchFromNetwork()
            } else {
                result.addSource(dbSource) { newDbData ->
                    setValue(Resource.success(newDbData))
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected open fun shouldFetch(dbData: ResultType?): Boolean =
        true // Always try to fetch the latest data

    @MainThread
    protected abstract fun createFetchCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun saveFetchCallResult(fetchData: RequestType, oldDbData: ResultType?)

    @WorkerThread
    protected open fun onFetchSucceed(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    private fun getValue() = result.value?.data

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.value = newValue
    }

    private fun fetchFromNetwork() {
        val apiSource = createFetchCall()

        result.addSource(apiSource) { response ->
            result.removeSource(apiSource)

            when (response) {
                is ApiSuccessResponse ->
                    appExecutors.diskIo().execute {
                        val fetchData = onFetchSucceed(response)
                        saveFetchCallResult(fetchData, getValue())

                        appExecutors.mainThread().execute {
                            // Request a new live data; otherwise it will return last cached value,
                            // which may not be updated with latest results received from network.
                            val newDbSource = loadFromDb()
                            result.addSource(newDbSource) { newData ->
                                result.removeSource(newDbSource)
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                is ApiEmptyResponse -> appExecutors.mainThread().execute {
                    // Reload from db.
                    val newDbSource = loadFromDb()
                    result.addSource(newDbSource) { dbData ->
                        result.removeSource(newDbSource)
                        setValue(Resource.success(dbData))
                    }
                }
                is ApiErrorResponse -> appExecutors.mainThread().execute {
                    setValue(Resource.error(response.error, null))
                }
            }
        }
    }

    companion object {

        @WorkerThread
        fun <T> syncDbData(dao: BaseDao<T>, oldDbData: T?, newDbData: T?) {
            oldDbData?.takeIf { newDbData == null }?.let { dbData -> dao.delete(dbData) }

            newDbData?.let { dbData -> dao.insert(dbData) }
        }

        @WorkerThread
        fun <T> syncDbData(dao: BaseDao<T>, oldDbData: Collection<T>?, newDbData: Collection<T>?) {
            oldDbData?.let { dbData ->
                val staleDbData = dbData.subtract(newDbData ?: emptyList())

                staleDbData.takeIf {
                    dbData.isNotEmpty()
                }?.let {
                    dao.delete(it)
                }
            }

            newDbData?.let { dbData -> dao.insert(dbData) }
        }
    }
}
