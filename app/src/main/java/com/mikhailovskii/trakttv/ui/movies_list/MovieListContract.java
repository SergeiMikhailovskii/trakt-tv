package com.mikhailovskii.trakttv.ui.movies_list;

import androidx.annotation.NonNull;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

import java.util.List;

public interface MovieListContract {

    interface MoviesListView extends MvpView {

        void onMovieListLoaded(@NonNull List<Movie> movieList);

        void onMovieListFailed();

    }

    interface MoviesListPresenter extends MvpPresenter<MoviesListView> {

        void loadMovieList();

    }

}
