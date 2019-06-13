package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.db.room.MovieDao;
import com.mikhailovskii.trakttv.db.room.MovieDatabase;
import com.mikhailovskii.trakttv.db.room.MovieEntity;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {

    @Override
    public void loadMovieDetails(@Nullable String slugId) {
        mCompositeDisposable.add(MovieAPIFactory.getInstance().getAPIService().getExtendedInfo(slugId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(it -> mView.showLoadingIndicator(true))
                .filter(movie -> slugId != null)
                .firstOrError()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe(result -> {
                            mView.showEmptyState(false);
                            mView.onMovieDetailsLoaded(result);
                        }, error -> {
                            mView.showEmptyState(true);
                            mView.onMovieDetailsFailed();
                        }
                )
        );
    }

    @Override
    public void addMovieToFavorites(@NonNull Bundle bundle) {
        MovieDao movieDao = MovieDatabase.getMovieDao();

        mCompositeDisposable.add(Observable.just(bundle)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .map(_bundle -> new MovieEntity(
                        _bundle.getString(MovieDetailActivity.EXTRA_NAME),
                        _bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS),
                        _bundle.getString(MovieListFragment.EXTRA_IMAGE),
                        _bundle.getString(MovieListFragment.EXTRA_SLUG)))
                .flatMap(movieEntity -> movieDao.insertMovie(movieEntity).toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe(result -> {
                    mView.onMoviesAdded();
                }, error -> {
                    Timber.e(error);
                    mView.onMovieDetailsFailed();
                })
        );

    }

}
