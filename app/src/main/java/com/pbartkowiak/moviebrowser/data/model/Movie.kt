package com.pbartkowiak.moviebrowser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pbartkowiak.moviebrowser.core.HasItemId
import kotlinx.android.parcel.Parcelize

@Suppress("ConstructorParameterNaming")
@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @PrimaryKey @ColumnInfo(name = "orderId") val orderId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "modificationDate") val modificationDate: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUrl") val image_url: String?
) : HasItemId<Int>, Parcelable {
    override fun getItemId() = orderId
}
