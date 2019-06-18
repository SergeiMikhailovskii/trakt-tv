package com.mikhailovskii.trakttv.ui.movies_list

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.MovieTrack
import com.mikhailovskii.trakttv.ui.base.BasePresenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

class MovieListPresenter : BasePresenter<MovieListContract.MoviesListView>(), MovieListContract.MoviesListPresenter {

    override fun loadMovieList() {
        mCompositeDisposable.add(MovieAPIFactory.instance.apiService.movies
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mView!!.showLoadingIndicator(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { mView!!.showLoadingIndicator(false) }
                .flatMapIterable { movieTracks -> movieTracks }
                .map(Function<MovieTrack, Movie> { this.getMovie(it) })
                .toList()
                .subscribe({ list ->
                    if (!list.isEmpty()) {
                        mView!!.showEmptyState(false)
                        mView!!.onMovieListLoaded(list)
                    } else {
                        mView!!.showEmptyState(true)
                    }
                }, { error ->
                    mView!!.showEmptyState(true)
                    mView!!.onMovieListFailed()
                }))

    }

    private fun getMovie(@NonNull movieTrack: MovieTrack): Movie {
        return Movie(IMG_URL,
                movieTrack.movie!!.name,
                movieTrack.movie!!.year,
                movieTrack.movie!!.movieId!!.slug,
                movieTrack.watchersNumber
        )
    }

    companion object {

        private val IMG_URL = "https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png"
    }

}