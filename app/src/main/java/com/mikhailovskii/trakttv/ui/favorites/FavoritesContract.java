package com.mikhailovskii.trakttv.ui.favorites;

import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface FavoritesContract {

    interface FavoritesView extends MvpView {

        void onMoviesLoaded();

        void onMoviesFailed();

    }

    interface FavoritesPresenter extends MvpPresenter<FavoritesView> {

        void loadFavoriteMovies();

    }

}
