package com.pbartkowiak.moviebrowser.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pbartkowiak.moviebrowser.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
}
