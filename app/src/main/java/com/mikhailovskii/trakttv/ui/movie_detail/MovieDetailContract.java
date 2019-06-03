package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface MovieDetailContract {

    interface MovieDetailView extends MvpView {

        void onMovieDetailsLoaded(@NonNull Movie movie);

        void onMovieDetailsFailed();

    }

    interface MovieDetailPresenter extends MvpPresenter<MovieDetailView> {

        void loadMovieDetails(@NonNull String slugId);

    }

}
