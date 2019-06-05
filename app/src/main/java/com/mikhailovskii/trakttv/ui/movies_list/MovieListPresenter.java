package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter extends BasePresenter<MovieListContract.MoviesListView>
        implements MovieListContract.MoviesListPresenter {

    @Override
    public void loadMovieList() {
        view.showLoadingIndicator(true);

        MovieAPIFactory.getInstance().getAPIService().getMovies()
                .enqueue(new Callback<List<MovieTrack>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<MovieTrack>> call,
                                           @NotNull Response<List<MovieTrack>> response) {
                        if (response.body() != null) {
                            List<Movie> list = new ArrayList<>();

                            for (MovieTrack movieTrack : response.body()) {
                                Movie movie = new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png",
                                        movieTrack.movie.getName(),
                                        movieTrack.movie.getYear(),
                                        movieTrack.movie.movieId.getSlug(),
                                        movieTrack.getWatchersNumber());
                                list.add(movie);
                            }

                            if (!list.isEmpty()) {
                                view.showEmptyState(false);
                                view.onMovieListLoaded(list);
                            } else {
                                view.showEmptyState(true);
                            }
                        } else  {
                            view.showEmptyState(true);
                            view.onMovieListFailed();
                        }
                        view.showLoadingIndicator(false);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<MovieTrack>> call,
                                          @NotNull Throwable throwable) {
                        view.onMovieListFailed();
                        view.showLoadingIndicator(false);
                    }
                });

/*        MovieAPIFactory.getInstance().getAPIService().getMovies1().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MovieTrack>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<MovieTrack> movieTracks) {
                for (MovieTrack movieTrack:movieTracks) {

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
    }

}
