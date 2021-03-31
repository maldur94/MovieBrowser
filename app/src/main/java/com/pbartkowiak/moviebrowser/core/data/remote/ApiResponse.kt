package com.pbartkowiak.moviebrowser.core.data.remote

import retrofit2.Response

/**
 * Common class used by API responses.
 *
 * @param <T> the type of the response object
 */
@Suppress("unused")
sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable) = ApiErrorResponse<T>(error.message ?: "Unknown error")

        fun <T> create(response: Response<T>) =
            if (response.isSuccessful) {
                createSuccessResponse(response)
            } else {
                createErrorResponse(response)
            }

        private fun <T> createErrorResponse(response: Response<T>): ApiErrorResponse<T> {
            val message = response.errorBody()?.string()
            val errorMsg = if (message.isNullOrEmpty()) response.message() else message

            return ApiErrorResponse(errorMsg ?: "Unknown error")
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

/**
 * Separate class to handle HTTP 204 status.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * Successful response from the API.
 */
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

/**
 * Error response from the API.
 */
data class ApiErrorResponse<T>(val error: String) : ApiResponse<T>()
