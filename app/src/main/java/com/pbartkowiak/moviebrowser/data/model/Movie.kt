package com.pbartkowiak.moviebrowser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pbartkowiak.moviebrowser.core.HasItemId
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @SerializedName("orderId") @PrimaryKey @ColumnInfo(name = "orderId") val orderId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("modificationDate") val modificationDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String
) : HasItemId<Int>, Parcelable {
    override fun getItemId() = orderId
}
