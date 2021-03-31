package com.pbartkowiak.moviebrowser.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Status of a resource that is provided to the UI.
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING,
    NULL;

    companion object {

        /**
         * Zips multiple [LiveData<Resource<*>>] into a single [ResourceStatus]. Used to show/hide progress bar
         * when loading data from multiple sources.
         */
        fun zip(vararg data: LiveData<out Resource<Any>>): LiveData<ResourceStatus> {
            val mediator = MediatorLiveData<ResourceStatus>()

            data.forEach { liveData: LiveData<out Resource<Any>> ->
                mediator.addSource(liveData) {
                    val statuses = data.map { resource ->
                        resource.value?.status
                    }

                    mediator.value = when {
                        statuses.indexOf(null) != -1 -> NULL
                        statuses.indexOf(LOADING) != -1 -> LOADING
                        statuses.indexOf(ERROR) != -1 -> ERROR
                        else -> SUCCESS
                    }
                }
            }

            return mediator
        }
    }
}
