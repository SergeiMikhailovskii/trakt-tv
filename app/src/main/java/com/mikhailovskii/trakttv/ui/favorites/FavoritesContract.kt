package com.mikhailovskii.trakttv.ui.favorites

import android.os.Bundle

import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.base.MvpPresenter
import com.mikhailovskii.trakttv.ui.base.MvpView

interface FavoritesContract {

    interface FavoritesView : MvpView {

        fun onMoviesLoaded(list: List<Movie>)

        fun onMoviesFailed()

        fun onMovieRemoved()

        fun onMovieRemoveFailed()

    }

    interface FavoritesPresenter : MvpPresenter<FavoritesView> {

        fun loadFavoriteMovies()

        fun deleteMovie(bundle: Bundle)

    }

}
