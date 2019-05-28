package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface MoviesListContract {

    interface MoviesListView extends MvpView {

        void onMoviesListDownloadSuccess();

        void onMovieInfoDownloadSuccess();

    }

    interface MoviesListPresenter extends MvpPresenter<MoviesListView> {

        void downloadMoviesList();

        void downloadMovieInfo();

    }

}
