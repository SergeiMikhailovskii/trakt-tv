package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                FavoritesModule::class,
                LoginModule::class
            ]
    )
    abstract fun bindMainActivity(): MainActivity


}