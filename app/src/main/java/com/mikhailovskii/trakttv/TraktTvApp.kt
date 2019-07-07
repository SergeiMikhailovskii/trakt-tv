package com.mikhailovskii.trakttv

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.ndk.CrashlyticsNdk
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mikhailovskii.trakttv.di.AppComponent
import com.mikhailovskii.trakttv.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class TraktTvApp : Application(), HasSupportFragmentInjector, HasActivityInjector {


    lateinit var appComponent: AppComponent

    @Inject
    lateinit var fragmentInjector:DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var activityInjector:DispatchingAndroidInjector<Activity>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        instance = this

        appComponent = buildComponent()
        appComponent.inject(this)

        initStetho()
        initFabric()


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

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }



    companion object {

        lateinit var instance: TraktTvApp
        lateinit var appContext: Context

    }

}
