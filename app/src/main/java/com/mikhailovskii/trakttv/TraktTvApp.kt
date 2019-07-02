package com.mikhailovskii.trakttv

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mikhailovskii.trakttv.di.AppComponent
import com.mikhailovskii.trakttv.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric

class TraktTvApp : Application() {

/*
    @Inject
    lateinit var appContext:Context
*/


    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext


        instance = this

        initStetho()
        initFabric()

        component = buildComponent()


        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->

                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    val token = task.result?.token
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

    protected fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }



    companion object {

        lateinit var instance: TraktTvApp
        lateinit var appContext: Context

        lateinit var component: AppComponent


    }

}
