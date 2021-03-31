package com.pbartkowiak.moviebrowser.core.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Collection<T>)

    @Delete
    fun delete(item: T)

    @Delete
    fun delete(items: Collection<T>)
}
