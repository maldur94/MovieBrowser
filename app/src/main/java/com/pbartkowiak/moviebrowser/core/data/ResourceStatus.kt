package com.pbartkowiak.moviebrowser.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING,
    NULL;

    companion object {

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
