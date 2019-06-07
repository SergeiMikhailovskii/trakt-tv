package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.Nullable;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

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

}
