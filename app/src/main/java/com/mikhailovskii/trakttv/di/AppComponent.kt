package com.mikhailovskii.trakttv.di

import com.mikhailovskii.trakttv.TraktTvApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance

        fun application(application: TraktTvApp): Builder

        fun build():AppComponent

    }

    fun inject(application: TraktTvApp)

}