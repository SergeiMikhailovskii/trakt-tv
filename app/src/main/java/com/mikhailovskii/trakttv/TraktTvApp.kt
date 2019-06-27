package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class TraktTvApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()
        initFabric()

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        Timber.w(task.exception, "getInstanceId failed")
                        return@OnCompleteListener
                    }

                    val token = task.result?.token
                    Timber.i(token)
                }
                )
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
