package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.model.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MoviesListContract {

    interface MoviesListView extends MvpView {

        void onMoviesListDownloadSuccess(List<Movie> movieList);

        void onMovieInfoDownloadSuccess();

    }

    interface MoviesListPresenter extends MvpPresenter<MoviesListView> {

        void downloadMoviesList() throws ExecutionException, InterruptedException;

        void downloadMovieInfo();

    }

}
