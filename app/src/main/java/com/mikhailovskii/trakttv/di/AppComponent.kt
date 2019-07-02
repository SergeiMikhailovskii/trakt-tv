package com.mikhailovskii.trakttv.di

import androidx.fragment.app.Fragment
import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.di.mvp.FavoritesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@Component(
        modules = [FavoritesModule::class]
)
interface AppComponent: AndroidInjector<TraktTvApp>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TraktTvApp): Builder

        fun build(): AppComponent

    }

    override fun inject(application: TraktTvApp)

    fun inject(fragment: Fragment)

}