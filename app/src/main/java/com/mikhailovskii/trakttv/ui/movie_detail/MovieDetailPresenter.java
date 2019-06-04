package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {

    @Override
    public void loadMovieDetails(@NonNull String slugId) {
        view.showLoadingIndicator(true);
        Call<Movie> extendedInfoCall = MovieAPIFactory.getInstance().getAPIService().getExtendedInfo(slugId);
        extendedInfoCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call,
                                   Response<Movie> response) {
                Movie movie = response.body();
                if (movie != null) {
                    view.onMovieDetailsLoaded(movie);
                } else {
                    view.onMovieDetailsFailed();
                }
            }

            @Override
            public void onFailure(Call<Movie> call,
                                  Throwable throwable) {
                view.onMovieDetailsFailed();
            }
        });
    }

}
