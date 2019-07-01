package com.mikhailovskii.trakttv

import android.app.Activity
import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mikhailovskii.trakttv.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class TraktTvApp : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: AndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity>? = androidInjector

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        instance = this

        initStetho()
        initFabric()

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    val token = task.result?.token
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
