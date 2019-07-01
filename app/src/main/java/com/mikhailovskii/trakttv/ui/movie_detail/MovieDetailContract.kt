package com.mikhailovskii.trakttv.ui.movie_detail

import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.base.MvpPresenter
import com.mikhailovskii.trakttv.ui.base.MvpView

interface MovieDetailContract {

    interface MovieDetailView : MvpView {

        fun onMovieDetailsLoaded(movie: Movie)

        fun onMovieDetailsFailed()

        fun onMoviesAdded()

        fun onMoviesAddingFailed()

        fun onMovieRemoved()

        fun onMovieRemoveFailed()

    }

    interface MovieDetailPresenter : MvpPresenter<MovieDetailView> {

        fun loadMovieDetails(slugId: String?)

        fun addMovieToFavorites(movie: Movie)

        fun removeMovieFromFavorites(name:String)

    }

}
