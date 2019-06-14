package com.mikhailovskii.trakttv.ui.favorites;

import android.os.Bundle;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

import java.util.List;

public interface FavoritesContract {

    interface FavoritesView extends MvpView {

        void onMoviesLoaded(List<Movie> list);

        void onMoviesFailed();

        void onMovieRemoved();

        void onMovieRemoveFailed();

    }

    interface FavoritesPresenter extends MvpPresenter<FavoritesView> {

        void loadFavoriteMovies();

        void deleteMovie(Bundle bundle);

    }

}
