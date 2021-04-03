package com.pbartkowiak.moviebrowser.core.data

import android.content.Context
import androidx.room.Room
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.data.source.AppDatabase

object DatabaseBuilder {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun build(context: Context): AppDatabase {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.config_database_name)
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
