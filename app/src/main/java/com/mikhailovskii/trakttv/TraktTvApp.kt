package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context

import com.facebook.stetho.Stetho
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import io.fabric.sdk.android.Fabric

class TraktTvApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()
        initFabric()
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics(), CrashlyticsNdk())
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
