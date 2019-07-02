package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.ui.favorites.FavoritesPresenter
import dagger.Module
import javax.inject.Inject

@Module
class FavoritesModule {

/*
    @Binds
    abstract fun favoritesPresenter(favoritesPresenter: FavoritesPresenter): FavoritesContract.FavoritesPresenter
*/

    @Inject
    fun favoritesPresenter(): FavoritesPresenter {
        return FavoritesPresenter()
    }


}