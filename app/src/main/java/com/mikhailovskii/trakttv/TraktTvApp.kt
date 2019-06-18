package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context

import com.facebook.stetho.Stetho

class TraktTvApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        lateinit var instance: TraktTvApp
        lateinit var appContext: Context
    }

}
