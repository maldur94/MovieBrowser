package com.pbartkowiak.moviebrowser.core.data.remote

import retrofit2.Response

private const val UNKNOWN_ERROR = "Unknown error"

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable) = ApiErrorResponse<T>(error.message ?: UNKNOWN_ERROR)

        fun <T> create(response: Response<T>) =
            if (response.isSuccessful) {
                createSuccessResponse(response)
            } else {
                createErrorResponse(response)
            }

        private fun <T> createErrorResponse(response: Response<T>): ApiErrorResponse<T> {
            val message = response.errorBody()?.string()
            val errorMsg = if (message.isNullOrEmpty()) response.message() else message

            return ApiErrorResponse(errorMsg ?: UNKNOWN_ERROR)
        }

        private fun <T> createSuccessResponse(response: Response<T>): ApiResponse<T> {
            val body = response.body()

            return if (body == null || response.code() == 204) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val error: String) : ApiResponse<T>()
