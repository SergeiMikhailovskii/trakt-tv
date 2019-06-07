package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(@NonNull MovieDetailContract.MovieDetailView movieDetailView) {
        super.attachView(movieDetailView);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeDisposable.clear();
    }

    @Override
    public void loadMovieDetails(@Nullable String slugId) {

        if (slugId != null) {
            view.showLoadingIndicator(true);
            final Movie[] movie = new Movie[1];

            view.showLoadingIndicator(true);

            mCompositeDisposable.add(MovieAPIFactory.getInstance().getAPIService().getExtendedInfo(slugId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            //onNext
                            movieRes -> movie[0] = movieRes,
                            //onError
                            throwable -> {
                                view.showEmptyState(true);
                                view.onMovieDetailsFailed();
                            },
                            //onCompleted
                            () -> {
                                view.showLoadingIndicator(false);
                                view.showEmptyState(false);
                                view.onMovieDetailsLoaded(movie[0]);
                            }
                    ));
        }

    }

}
