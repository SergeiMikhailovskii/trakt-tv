package com.mikhailovskii.trakttv.ui.movie_detail

import android.os.Bundle
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.Optional
import com.mikhailovskii.trakttv.db.room.MovieDatabase
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieDetailPresenter : BasePresenter<MovieDetailContract.MovieDetailView>(), MovieDetailContract.MovieDetailPresenter {

    override fun loadMovieDetails(slugId: String?) {
        mCompositeDisposable.add(Observable.just(Optional(slugId))
                .filter { !it.isEmpty }
                .firstOrError()
                .toObservable()
                .map { it.get() }
                .flatMap { MovieAPIFactory.getInstance().apiService.getExtendedInfo(it) }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mView!!.showLoadingIndicator(true) }
                .filter { slugId != null }
                .firstOrError()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { mView!!.showLoadingIndicator(false) }
                .subscribe({ result ->
                    mView!!.showEmptyState(false)
                    mView!!.onMovieDetailsLoaded(result)
                }, {
                    mView!!.showEmptyState(true)
                    mView!!.onMovieDetailsFailed()
                })
        )
    }

    override fun addMovieToFavorites(bundle: Bundle) {
        val movieDao = MovieDatabase.movieDao

        mCompositeDisposable.add(Observable.just(bundle)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mView!!.showLoadingIndicator(true) }
                .map { _bundle ->
                    Movie(
                            _bundle.getString(MovieDetailActivity.EXTRA_NAME),
                            _bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS),
                            _bundle.getString(MovieListFragment.EXTRA_IMAGE),
                            _bundle.getString(MovieListFragment.EXTRA_SLUG))
                }
                .flatMap<Any> { movieEntity -> movieDao.insertMovie(movieEntity).toObservable() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { mView!!.onMoviesAdded() }
                .doOnError { error ->
                    Timber.e(error)
                    mView!!.onMovieDetailsFailed()
                }
                .doAfterTerminate { mView!!.showLoadingIndicator(false) }
                .subscribe()
        )

    }

}
