package com.mikhailovskii.trakttv.ui.favorites;

import android.os.Bundle;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.db.room.MovieDao;
import com.mikhailovskii.trakttv.db.room.MovieDatabase;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FavoritesPresenter extends BasePresenter<FavoritesContract.FavoritesView>
        implements FavoritesContract.FavoritesPresenter {

    @Override
    public void loadFavoriteMovies() {
        MovieDao movieDao = MovieDatabase.getMovieDao();

        mCompositeDisposable.add(movieDao.getFavorites()
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .toObservable()
                .flatMapIterable(listObservable -> listObservable)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe(list -> {
                    if (list.isEmpty()) {
                        mView.showEmptyState(true);
                    } else {
                        mView.showEmptyState(false);
                        mView.onMoviesLoaded(list);
                    }
                }, Timber::e)
        );

    }

    @Override
    public void deleteMovie(Bundle bundle) {

        mCompositeDisposable.add(Observable.just(bundle)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .map(_bundle -> new Movie(_bundle.getString(MovieDetailActivity.EXTRA_NAME),
                        _bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS),
                        _bundle.getString(MovieListFragment.EXTRA_IMAGE),
                        _bundle.getString(MovieListFragment.EXTRA_SLUG)))
                .flatMap(movieEntity -> MovieDatabase.getMovieDao().deleteMovie(movieEntity.getName()).toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe(
                        result -> mView.onMovieRemoved(),
                        error -> {
                            mView.onMovieRemoveFailed();
                            Timber.e(error);
                        }
                )
        );

    }

}
