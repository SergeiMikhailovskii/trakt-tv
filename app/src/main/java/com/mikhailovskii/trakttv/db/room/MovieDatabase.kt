package com.mikhailovskii.trakttv.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.db.room.MovieDatabase.Companion.DB_VERSION

@Database(
        entities = [Movie::class],
        version = DB_VERSION,
        exportSchema = false
)
@TypeConverters(RoomTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        const val DB_VERSION = 6
        private const val DB_NAME = "trakttv.db"

        val movieDao: MovieDao
            get() = getInstance(TraktTvApp.appContext).movieDao()

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        private fun getInstance(context: Context): MovieDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }

}
