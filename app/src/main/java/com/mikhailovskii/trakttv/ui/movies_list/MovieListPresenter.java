package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends BasePresenter<MovieListContract.MoviesListView>
        implements MovieListContract.MoviesListPresenter {

    private static final String IMG_URL = "https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png";

    @Override
    public void loadMovieList() {
        view.showLoadingIndicator(true);

        List<Movie> list = new ArrayList<>();

        MovieAPIFactory.getInstance().getAPIService()
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieTrack>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MovieTrack> movieTracks) {
                        for (MovieTrack movieTrack : movieTracks) {
                            Movie movie = new Movie(IMG_URL,
                                    movieTrack.movie.getName(),
                                    movieTrack.movie.getYear(),
                                    movieTrack.movie.movieId.getSlug(),
                                    movieTrack.getWatchersNumber());
                            list.add(movie);
                        }
                    }

                    @Override
                    public void onError(Throwable exception) {
                        view.showEmptyState(true);
                        view.onMovieListFailed();
                    }

                    @Override
                    public void onComplete() {
                        if (!list.isEmpty()) {
                            view.showEmptyState(false);
                            view.onMovieListLoaded(list);
                        } else {
                            view.showEmptyState(true);
                        }
                    }
                });

        view.showLoadingIndicator(false);

    }

}
