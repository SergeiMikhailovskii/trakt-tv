package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mikhailovskii.trakttv.di.AppModule.apiModule
import com.mikhailovskii.trakttv.di.AppModule.appModule
import com.mikhailovskii.trakttv.di.AppModule.dbModule
import com.mikhailovskii.trakttv.di.AppModule.mvpModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TraktTvApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        instance = this

        initStetho()
        initFabric()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@TraktTvApp)
            modules(appModule, apiModule, dbModule, mvpModule)
        }

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                })
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
