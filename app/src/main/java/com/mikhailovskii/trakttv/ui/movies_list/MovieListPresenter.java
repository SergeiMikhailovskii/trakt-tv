package com.mikhailovskii.trakttv.ui.movies_list;

import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends BasePresenter<MovieListContract.MoviesListView>
        implements MovieListContract.MoviesListPresenter {

    private static final String IMG_URL = "https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png";
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(@NonNull MovieListContract.MoviesListView moviesListView) {
        super.attachView(moviesListView);
        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeDisposable.clear();
    }

    @Override
    public void loadMovieList() {
        List<Movie> list = new ArrayList<>();

        view.showLoadingIndicator(true);
        mCompositeDisposable.add(MovieAPIFactory.getInstance().getAPIService().getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        movieTracks -> {
                            for (MovieTrack movieTrack : movieTracks) {
                                Movie movie = new Movie(IMG_URL,
                                        movieTrack.movie.getName(),
                                        movieTrack.movie.getYear(),
                                        movieTrack.movie.movieId.getSlug(),
                                        movieTrack.getWatchersNumber());
                                list.add(movie);
                            }
                        },
                        //onError
                        throwable -> {
                            view.showEmptyState(true);
                            view.onMovieListFailed();
                        },
                        //onCompleted
                        () -> {
                            view.showLoadingIndicator(false);
                            if (!list.isEmpty()) {
                                view.showEmptyState(false);
                                view.onMovieListLoaded(list);
                            } else {
                                view.showEmptyState(true);
                            }
                        }

                ));

    }


}
