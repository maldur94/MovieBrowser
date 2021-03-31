package com.pbartkowiak.moviebrowser.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pbartkowiak.moviebrowser.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun provideMoviesDao(): MovieDao
}
