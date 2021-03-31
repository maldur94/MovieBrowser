package com.pbartkowiak.moviebrowser.core.data

import timber.log.Timber

/**
 * A generic class that holds a value with its loading status.
 */
@Suppress("DataClassContainsFunctions")
data class Resource<out E>(val status: ResourceStatus, val data: E?, val message: String? ) {

    fun hasData() = when (data) {
        is Collection<*> -> data.isNotEmpty()
        is Map<*, *> -> data.isNotEmpty()
        else -> data != null
    }

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

        fun <T> errorCommand(data: T?, message: String?): Resource<T> {
            Timber.e("Resource.error(msg='$message', data='$data')")
            return Resource(ResourceStatus.ERROR, data, message)
        }
    }
}
