package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.di.scope.FragmentScoped
import com.mikhailovskii.trakttv.ui.movies_list.MovieListContract
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import com.mikhailovskii.trakttv.ui.movies_list.MovieListPresenter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindMovieListFragment(): MovieListFragment

    @ActivityScoped
    @Binds
    abstract fun movieListPresenter(movieListPresenter: MovieListPresenter): MovieListContract.MovieListPresenter

}