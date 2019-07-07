package com.mikhailovskii.trakttv.ui.movies_list

import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.base.MvpPresenter
import com.mikhailovskii.trakttv.ui.base.MvpView

interface MovieListContract {

    interface MovieListView : MvpView {

        fun onMovieListLoaded(movieList: List<Movie>)

        fun onMovieListFailed()

    }

    interface MovieListPresenter : MvpPresenter<MovieListView> {

        fun loadMovieList()

    }

}
