package com.pbartkowiak.moviebrowser.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pbartkowiak.moviebrowser.core.data.local.BaseDao
import com.pbartkowiak.moviebrowser.data.model.Movie

@Dao
abstract class MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie ORDER BY orderId ASC")
    abstract fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<Movie>)
}
