package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.room.MovieDao;
import com.mikhailovskii.trakttv.data.room.MovieDatabase;
import com.mikhailovskii.trakttv.data.room.MovieEntity;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

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
    public void addMovieToFavorites(@NonNull Bundle bundle) {
        String url = bundle.getString(MovieListFragment.EXTRA_IMAGE);
        int watchers = bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS);
        String name = bundle.getString(MovieDetailActivity.EXTRA_NAME);

        MovieDatabase database = TraktTvApp.getInstance().getDatabase();
        MovieDao movieDao = database.movieDao();
        MovieEntity movieEntity = new MovieEntity(name, watchers, url);
        movieDao.insertMovie(movieEntity);

        mView.onMoviesAdded();


    }

}
