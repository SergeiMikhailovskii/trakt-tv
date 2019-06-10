package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface MovieDetailContract {

    interface MovieDetailView extends MvpView {

        void onMovieDetailsLoaded(@NonNull Movie movie);

        void onMovieDetailsFailed();

        void onMoviesAdded();

        void onMoviesAddingFailed();

    }

    interface MovieDetailPresenter extends MvpPresenter<MovieDetailView> {

        void loadMovieDetails(@Nullable String slugId);

        void addMovieToFavorites(@NonNull Bundle bundle);

    }

}
