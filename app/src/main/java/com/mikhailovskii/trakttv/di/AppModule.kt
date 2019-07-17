package com.mikhailovskii.trakttv.di

import android.preference.PreferenceManager
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory
import com.mikhailovskii.trakttv.db.room.MovieDatabase
import com.mikhailovskii.trakttv.ui.favorites.FavoritesContract
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment
import com.mikhailovskii.trakttv.ui.favorites.FavoritesPresenter
import com.mikhailovskii.trakttv.ui.login.LoginActivity
import com.mikhailovskii.trakttv.ui.login.LoginContract
import com.mikhailovskii.trakttv.ui.login.LoginPresenter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailContract
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailPresenter
import com.mikhailovskii.trakttv.ui.movies_list.MovieListContract
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import com.mikhailovskii.trakttv.ui.movies_list.MovieListPresenter
import com.mikhailovskii.trakttv.ui.profile.ProfileContract
import com.mikhailovskii.trakttv.ui.profile.ProfileFragment
import com.mikhailovskii.trakttv.ui.profile.ProfilePresenter
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModule {

    val appModule = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    }

    val apiModule = module {
        single { MovieAPIFactory.getInstance().apiService }
    }

    val dbModule = module {
        single { MovieDatabase.movieDao }
    }

    val mvpModule = module {

        scope(named<FavoritesFragment>()) {
            scoped { FavoritesPresenter(get()) as FavoritesContract.FavoritesPresenter }
        }

        scope(named<LoginActivity>()) {
            scoped { LoginPresenter() as LoginContract.LoginPresenter }
        }

        scope(named<MovieDetailActivity>()) {
            scoped { MovieDetailPresenter(get()) as MovieDetailContract.MovieDetailPresenter }
        }

        scope(named<MovieListFragment>()) {
            scoped { MovieListPresenter(get()) as MovieListContract.MovieListPresenter }
        }

        scope(named<ProfileFragment>()) {
            scoped { ProfilePresenter() as ProfileContract.ProfilePresenter }
        }

    }

}