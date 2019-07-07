package com.mikhailovskii.trakttv.di

import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.di.mvp.BuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AndroidSupportInjectionModule::class,
            BuildersModule::class]
)
interface AppComponent : AndroidInjector<TraktTvApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TraktTvApp): Builder

        fun build(): AppComponent

    }

    override fun inject(application: TraktTvApp)

}