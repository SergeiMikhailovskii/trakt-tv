package com.mikhailovskii.trakttv.di

import android.content.Context
import com.mikhailovskii.trakttv.TraktTvApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: TraktTvApp): Context {
        return application.applicationContext
    }

}