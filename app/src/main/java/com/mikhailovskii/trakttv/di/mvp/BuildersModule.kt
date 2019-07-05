package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.ui.login.LoginActivity
import com.mikhailovskii.trakttv.ui.main.MainActivity
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                LoginModule::class,
                MovieListModule::class
            ]
    )
    abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                LoginModule::class
            ]
    )
    abstract fun bindLoginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                MovieDetailModule::class
            ]
    )
    abstract fun bindMovieDetailActivity(): MovieDetailActivity


}