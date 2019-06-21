package com.mikhailovskii.trakttv.ui.favorites

import android.os.Bundle

import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.db.room.MovieDatabase
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoritesPresenter : BasePresenter<FavoritesContract.FavoritesView>(), FavoritesContract.FavoritesPresenter {

    override fun loadFavoriteMovies() {
        val movieDao = MovieDatabase.movieDao

        mCompositeDisposable.add(movieDao.getFavorites()
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe {
                    mView!!.showLoadingIndicator(true)
                }
                .toObservable()
                .flatMapIterable {
                    listObservable -> listObservable
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { list ->
                    if (list.isEmpty()) {
                        mView!!.showEmptyState(true)
                    } else {
                        mView!!.showEmptyState(false)
                        mView!!.onMoviesLoaded(list)
                    }
                }
                .doOnError { error ->
                    mView!!.onMoviesFailed()
                    Timber.e(error)
                }
                .doAfterTerminate {
                    mView!!.showLoadingIndicator(false)
                }
                .subscribe()
        )

    }

    override fun deleteMovie(name: String) {

        mCompositeDisposable.add(Observable.just(name)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mView!!.showLoadingIndicator(true)
                }
                .firstOrError()
                .toObservable()
                .flatMap<Any> {
                    MovieDatabase.movieDao.deleteMovie(name).toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    mView!!.onMovieRemoved()
                }
                .doOnError { error ->
                    mView!!.onMovieRemoveFailed()
                    Timber.e(error)
                }
                .doAfterTerminate { mView!!.showLoadingIndicator(false) }
                .subscribe()
        )

    }

}
