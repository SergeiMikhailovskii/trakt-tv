package com.mikhailovskii.trakttv.ui.movie_detail

import com.mikhailovskii.trakttv.data.api.MovieAPI
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.Optional
import com.mikhailovskii.trakttv.db.room.MovieDatabase
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenter(
        private val apiService: MovieAPI
) : BasePresenter<MovieDetailContract.MovieDetailView>(), MovieDetailContract.MovieDetailPresenter {

    override fun loadMovieDetails(slugId: String?) {
        compositeDisposable.add(Observable.just(Optional(slugId))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view?.showLoadingIndicator(true) }
                .filter { !it.isEmpty }
                .firstOrError()
                .toObservable()
                .map { it.get() }
                .flatMap { apiService.getExtendedInfo(it) }
                .flatMap { serverMovie ->
                    MovieDatabase.movieDao.getMovie(serverMovie.name!!)
                            .toObservable()
                            .map { localMovie -> combineLocalDataWithServer(serverMovie, localMovie) }
                            .onErrorReturn { serverMovie }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view?.showLoadingIndicator(false) }
                .subscribe({ result ->
                    view?.showEmptyState(false)
                    view?.onMovieDetailsLoaded(result)
                }, {
                    view?.onMovieDetailsFailed()
                })
        )
    }

    override fun addMovieToFavorites(movie: Movie) {
        compositeDisposable.add(Observable.just(movie)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    it.isFavorite = true
                    Observable.fromCallable { MovieDatabase.movieDao.insertMovie(it) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onMoviesAdded()
                }, {
                    view?.onMoviesAddingFailed()
                })
        )
    }

    override fun removeMovieFromFavorites(name: String) {
        compositeDisposable.add(Observable.just(name)
                .subscribeOn(Schedulers.io())
                .firstOrError()
                .toObservable()
                .flatMap<Any> {
                    MovieDatabase.movieDao.deleteMovie(name).toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    view?.onMovieRemoved()
                }
                .doOnError {
                    view?.onMovieRemoveFailed()
                }
                .subscribe()
        )

    }

    private fun combineLocalDataWithServer(
            serverMovie: Movie,
            localMovie: Movie
    ): Movie {
        serverMovie.isFavorite = localMovie.isFavorite

        return serverMovie
    }

}
