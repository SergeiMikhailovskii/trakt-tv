package com.mikhailovskii.trakttv.db.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.data.entity.Movie

@Database(entities = [Movie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        val movieDao: MovieDao
            get() = TraktTvApp.instance.database!!.movieDao()
    }

}
