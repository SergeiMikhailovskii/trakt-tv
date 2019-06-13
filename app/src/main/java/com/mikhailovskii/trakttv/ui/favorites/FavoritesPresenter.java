package com.mikhailovskii.trakttv.ui.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.db.room.MovieDao;
import com.mikhailovskii.trakttv.db.room.MovieDatabase;
import com.mikhailovskii.trakttv.db.room.MovieEntity;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FavoritesPresenter extends BasePresenter<FavoritesContract.FavoritesView>
        implements FavoritesContract.FavoritesPresenter {

    @Override
    public void loadFavoriteMovies() {
        MovieDatabase database = TraktTvApp.getInstance().getDatabase();
        MovieDao movieDao = database.movieDao();

        mCompositeDisposable.add(movieDao.getFavorites()
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .toObservable()
                .flatMapIterable(listObservable -> listObservable)
                .map(this::getMovie)
                .toList()
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (list.isEmpty()) {
                        mView.showEmptyState(true);
                    } else {
                        mView.showEmptyState(false);
                        mView.onMoviesLoaded(list);
                    }
                }, Timber::e)
        );

    }

    @Override
    public void deleteMovie(Bundle bundle) {
        String url = bundle.getString(MovieListFragment.EXTRA_IMAGE);
        int watchers = bundle.getInt(MovieDetailActivity.EXTRA_WATCHERS);
        String name = bundle.getString(MovieDetailActivity.EXTRA_NAME);
        String slug = bundle.getString(MovieListFragment.EXTRA_SLUG);

        MovieEntity movieEntity = new MovieEntity(name, watchers, url, slug);

        MovieDatabase database = TraktTvApp.getInstance().getDatabase();
        MovieDao movieDao = database.movieDao();
        mCompositeDisposable.add(movieDao.deleteMovie(movieEntity)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mView.showLoadingIndicator(true))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> mView.onMovieDeleteFailed())
                .doOnComplete(() -> mView.onMovieDeleted())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .subscribe()
        );

    }

    @NonNull
    private Movie getMovie(@NonNull MovieEntity movieEntity) {
        return new Movie(movieEntity.getIconUrl(),
                movieEntity.getName(),
                0,
                movieEntity.getSlug(),
                movieEntity.getWatchers()
        );
    }

}
