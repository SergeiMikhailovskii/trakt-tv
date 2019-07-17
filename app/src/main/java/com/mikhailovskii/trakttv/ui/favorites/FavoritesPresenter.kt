package com.mikhailovskii.trakttv.ui.favorites

import com.mikhailovskii.trakttv.db.room.MovieDatabase
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoritesPresenter : BasePresenter<FavoritesContract.FavoritesView>(), FavoritesContract.FavoritesPresenter {


    override fun loadFavoriteMovies() {
        val movieDao = MovieDatabase.movieDao

        compositeDisposable.add(movieDao.getFavorites()
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe {
                    view?.showLoadingIndicator(true)
                }
                .toObservable()
                .flatMapIterable { listObservable ->
                    listObservable
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { list ->
                    if (list.isEmpty()) {
                        view?.showEmptyState(true)
                    } else {
                        view?.showEmptyState(false)
                        view?.onMoviesLoaded(list)
                    }
                }
                .doOnError {
                    view?.onMoviesFailed()
                }
                .doAfterTerminate {
                    view?.showLoadingIndicator(false)
                }
                .subscribe()
        )

    }

    override fun deleteMovie(name: String) {

        compositeDisposable.add(Observable.just(name)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    view?.showLoadingIndicator(true)
                }
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
                .doAfterTerminate { view?.showLoadingIndicator(false) }
                .subscribe()
        )

    }

}
