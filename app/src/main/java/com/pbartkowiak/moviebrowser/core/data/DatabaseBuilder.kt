package com.pbartkowiak.moviebrowser.core.data

import android.content.Context
import androidx.room.Room
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.data.MovieDatabase

object DatabaseBuilder {

    @Volatile
    private var INSTANCE: MovieDatabase? = null

    fun build(context: Context): MovieDatabase {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    context.getString(R.string.config_database_name)
                ).build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
