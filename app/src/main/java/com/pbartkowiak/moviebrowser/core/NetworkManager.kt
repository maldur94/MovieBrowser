package com.pbartkowiak.moviebrowser.core

import android.content.Context
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.data.source.MovieService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val SERVER_TIMEOUT_SECONDS = 30L
const val API_PAGE = 0L
const val API_DATA_SIZE = 1000L

class NetworkManager(private val context: Context) {

    fun provideMovieService(): MovieService = retrofit().create(MovieService::class.java)

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.config_network_api_url))
        .client(initOkHttpClient())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun initOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .retryOnConnectionFailure(true)
            .addInterceptor(initLoggingInterceptor())
            .connectTimeout(SERVER_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(SERVER_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(SERVER_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .cache(
                Cache(
                    context.cacheDir,
                    context.resources.getInteger(R.integer.config_network_cache_size_mb)
                        .toLong() * 1024 * 1024
                )
            )

        return builder.build()
    }

    private fun initLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            print(message)
        }

        loggingInterceptor.level = getLoggingLevel()
        return loggingInterceptor
    }

    private fun getLoggingLevel() = HttpLoggingInterceptor.Level.BODY
}
