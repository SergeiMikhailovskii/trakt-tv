package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context
import androidx.room.Room

import com.facebook.stetho.Stetho
import com.mikhailovskii.trakttv.db.room.MovieDatabase

class TraktTvApp : Application() {
    var database: MovieDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
        database = Room.databaseBuilder<MovieDatabase>(this, MovieDatabase::class.java!!, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        setupDebugTools()
    }

    private fun setupDebugTools() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {

        var instance: TraktTvApp

        private val DB_NAME = "MovieDatabase"
        var appContext: Context? = null
            private set
    }

}
