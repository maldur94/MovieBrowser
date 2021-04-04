package com.pbartkowiak.moviebrowser.core.data

import timber.log.Timber

@Suppress("DataClassContainsFunctions")
data class Resource<out E>(val status: ResourceStatus, val data: E?, val message: String?) {

    companion object {

        fun <T> loading(data: T? = null): Resource<T> {
            Timber.d("Resource.loading(data='$data')")
            return Resource(ResourceStatus.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            Timber.i("Resource.success(data='$data')")
            return Resource(ResourceStatus.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T?): Resource<T> {
            Timber.e("Resource.error(msg='$message', data='$data')")
            return Resource(ResourceStatus.ERROR, data, message)
        }
    }
}
