package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

import java.util.List;

public interface MoviesListContract {

    interface MoviesListView extends MvpView {

        void onMovieListLoaded(List<Movie> movieList);

        void onMovieListFailed();

    }

    interface MoviesListPresenter extends MvpPresenter<MoviesListView> {

        void loadMovieList();

    }

}
