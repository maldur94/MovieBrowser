package com.pbartkowiak.moviebrowser.data.source

import com.google.gson.annotations.SerializedName
import com.pbartkowiak.moviebrowser.data.model.Movie

fun MovieDto.toEntity() = Movie(
    orderId = orderId,
    title = title,
    modificationDate = modificationDate,
    description = description,
    imageUrl = image_url
)

data class MovieDto(
    @SerializedName("orderId") val orderId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("modificationDate") val modificationDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String
)
