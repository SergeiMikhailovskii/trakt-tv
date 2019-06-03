package com.mikhailovskii.trakttv.ui.movies_list;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListPresenter extends BasePresenter<MoviesListContract.MoviesListView>
        implements MoviesListContract.MoviesListPresenter {

    @Override
    public void loadMovieList() {
        view.showLoadingIndicator(true);

        MovieAPIFactory.getInstance().getAPIService().getMovies()
                .enqueue(new Callback<List<MovieTrack>>() {
                    @Override
                    public void onResponse(Call<List<MovieTrack>> call,
                                           Response<List<MovieTrack>> response) {
                        if (response.body() != null) {
                            List<Movie> list = new ArrayList<>();

                            for (MovieTrack movieTrack : response.body()) {
                                Movie movie = new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png",
                                        movieTrack.movie.getName(),
                                        movieTrack.movie.getYear(),
                                        movieTrack.movie.movieId.getSlug());
                                list.add(movie);
                            }

                            if (!list.isEmpty()) {
                                view.onMovieListLoaded(list);
                            } else {
                                // view.showEmptyState(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MovieTrack>> call,
                                          Throwable throwable) {
                        view.onMovieListFailed();
                    }
                });
    }

}
