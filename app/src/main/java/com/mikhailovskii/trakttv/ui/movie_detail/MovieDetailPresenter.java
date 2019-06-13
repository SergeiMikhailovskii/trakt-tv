package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.room.MovieDao;
import com.mikhailovskii.trakttv.data.room.MovieDatabase;
import com.mikhailovskii.trakttv.data.room.MovieEntity;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void addMovieToFavorites(@Nonnull Bundle bundle) {
        mView.showLoadingIndicator(true);
        String url = bundle.getString(MovieListFragment.EXTRA_IMAGE);
        int watchers = bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS);
        String name = bundle.getString(MovieDetailActivity.EXTRA_NAME);
        String slug = bundle.getString(MovieListFragment.EXTRA_SLUG);

        MovieDatabase database = TraktTvApp.getInstance().getDatabase();
        MovieDao movieDao = database.movieDao();
        MovieEntity movieEntity = new MovieEntity(name, watchers, url, slug);

        mCompositeDisposable.add(movieDao.insertMovie(movieEntity)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> mView.onMovieDetailsFailed())
                .doOnComplete(() -> mView.onMoviesAdded())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe()
        );

/*        movieDao.insertMovie(movieEntity);
        mView.onMoviesAdded();
        mView.showLoadingIndicator(false);*/
    }

}
