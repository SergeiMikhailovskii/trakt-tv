package com.mikhailovskii.trakttv.ui.favorites;

import com.mikhailovskii.trakttv.ui.base.BasePresenter;

public class FavoritesPresenter extends BasePresenter<FavoritesContract.FavoritesView>
        implements FavoritesContract.FavoritesPresenter {

    @Override
    public void loadFavoriteMovies() {
        mView.onMoviesFailed();
    }

}
