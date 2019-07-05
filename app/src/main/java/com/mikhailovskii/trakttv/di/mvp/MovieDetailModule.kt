package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailContract
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailModule {

    @ActivityScoped
    @Binds
    abstract fun movieDetailPresenter(movieDetailPresenter: MovieDetailPresenter): MovieDetailContract.MovieDetailPresenter

}