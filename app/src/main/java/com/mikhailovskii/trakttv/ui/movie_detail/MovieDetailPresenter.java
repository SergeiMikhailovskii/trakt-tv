package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.Nullable;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {

    @Override
    public void loadMovieDetails(@Nullable String slugId) {

        if (slugId != null) {
            MovieAPIFactory.getInstance().getAPIService()
                    .getExtendedInfo(slugId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Movie>() {

                        Movie movie = null;

                        @Override
                        public void onSubscribe(Disposable d) {
                            view.showLoadingIndicator(true);
                        }

                        @Override
                        public void onNext(Movie movieRes) {
                            movie = movieRes;
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showEmptyState(true);
                            view.onMovieDetailsFailed();
                        }

                        @Override
                        public void onComplete() {
                            view.showLoadingIndicator(false);
                            if (movie != null) {
                                view.showEmptyState(false);
                                view.onMovieDetailsLoaded(movie);
                            } else {
                                view.showEmptyState(true);
                            }
                        }
                    });
        }

    }

}
