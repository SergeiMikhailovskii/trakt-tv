package com.mikhailovskii.trakttv.ui.movies_list

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.data.entity.MovieTrack
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListPresenter : BasePresenter<MovieListContract.MoviesListView>(), MovieListContract.MoviesListPresenter {

    override fun loadMovieList() {
        mCompositeDisposable.add(MovieAPIFactory.getInstance().apiService.getMovies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mView!!.showLoadingIndicator(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { mView!!.showLoadingIndicator(false) }
                .flatMapIterable { movieTracks -> movieTracks }
                .map { getMovie(it) }
                .toList()
                .subscribe({ list ->
                    if (list.isNotEmpty()) {
                        mView!!.showEmptyState(false)
                        mView!!.onMovieListLoaded(list)
                    } else {
                        mView!!.showEmptyState(true)
                    }
                }, {
                    mView!!.onMovieListFailed()
                }))

    }

    private fun getMovie(movieTrack: MovieTrack): Movie {
        return Movie(IMG_URL,
                movieTrack.movie?.name!!,
                movieTrack.movie?.year!!,
                movieTrack.movie?.movieId?.slug!!,
                movieTrack.watchersNumber
        )
    }

    companion object {

        private const val IMG_URL = "https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png"

    }

}
