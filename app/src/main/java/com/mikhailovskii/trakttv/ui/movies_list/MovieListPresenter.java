package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends BasePresenter<MovieListContract.MoviesListView>
        implements MovieListContract.MoviesListPresenter {

    private static final String IMG_URL = "https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png";

    @Override
    public void loadMovieList() {
        mCompositeDisposable.add(MovieAPIFactory.getInstance().getAPIService().getMovies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(it -> mView.showLoadingIndicator(true))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mView.showLoadingIndicator(false))
                .flatMapIterable(movieTracks -> movieTracks)
                .map(this::getMovie)
                .toList()
                .subscribe(list -> {
                    if (!list.isEmpty()) {
                        mView.showEmptyState(false);
                        mView.onMovieListLoaded(list);
                    } else {
                        mView.showEmptyState(true);
                    }
                }, error -> {
                    mView.showEmptyState(true);
                    mView.onMovieListFailed();
                }));

    }

    @NotNull
    private Movie getMovie(@NonNull MovieTrack movieTrack) {
        return new Movie(IMG_URL,
                movieTrack.movie.getName(),
                movieTrack.movie.getYear(),
                movieTrack.movie.movieId.getSlug(),
                movieTrack.getWatchersNumber()
        );
    }

}
