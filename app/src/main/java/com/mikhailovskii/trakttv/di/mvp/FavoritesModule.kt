package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.di.scope.FragmentScoped
import com.mikhailovskii.trakttv.ui.favorites.FavoritesContract
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment
import com.mikhailovskii.trakttv.ui.favorites.FavoritesPresenter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindFavoritesFragment():FavoritesFragment

    @ActivityScoped
    @Binds
    abstract fun favoritesPresenter(presenter: FavoritesPresenter): FavoritesContract.FavoritesPresenter

}