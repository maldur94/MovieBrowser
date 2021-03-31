package com.pbartkowiak.moviebrowser.core.data

import android.app.Application
import androidx.room.Room
import com.pbartkowiak.moviebrowser.R
import com.pbartkowiak.moviebrowser.data.source.AppDatabase

object DatabaseProvider {

    fun provideDatabaseManager(application: Application) =
        application.run {
            val dbName = resources.getString(R.string.config_database_name)
            Room.databaseBuilder(this, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
}
