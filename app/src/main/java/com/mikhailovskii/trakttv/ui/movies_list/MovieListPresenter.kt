package com.mikhailovskii.trakttv.ui.movies_list

import com.mikhailovskii.trakttv.data.api.MovieAPI
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.MovieTrack
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListPresenter(
        private val apiService:MovieAPI
) : BasePresenter<MovieListContract.MovieListView>(), MovieListContract.MovieListPresenter {

    override fun loadMovieList() {
        compositeDisposable.add(apiService.getMovies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view?.showLoadingIndicator(true) }
                .flatMapIterable { movieTracks -> movieTracks }
                .map { getMovie(it) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view?.showLoadingIndicator(false) }
                .subscribe({ list ->
                    if (list.isNotEmpty()) {
                        view?.showEmptyState(false)
                        view?.onMovieListLoaded(list)
                    } else {
                        view?.showEmptyState(true)
                    }
                }, {
                    view?.onMovieListFailed()
                }))

    }

    private fun getMovie(movieTrack: MovieTrack): Movie {
        return Movie(
                name = movieTrack.movie?.name!!,
                year = movieTrack.movie?.year!!,
                movieId = movieTrack.movie?.movieId!!,
                watchers = movieTrack.watchersNumber
        )
    }

}
