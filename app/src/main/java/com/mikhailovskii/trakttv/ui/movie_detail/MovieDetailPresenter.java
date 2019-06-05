package com.mikhailovskii.trakttv.ui.movie_detail;

import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.data.api.MovieAPIFactory;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import org.jetbrains.annotations.NotNull;

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
            public void onResponse(@NotNull Call<Movie> call,
                                   @NotNull Response<Movie> response) {
                Movie movie = response.body();
                if (movie != null) {
                    view.showEmptyState(false);
                    view.onMovieDetailsLoaded(movie);
                } else {
                    view.showEmptyState(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call,
                                  @NotNull Throwable throwable) {
                view.showEmptyState(true);
                view.onMovieDetailsFailed();
            }
        });

/*        Observable.create(emitter -> {
            Call<Movie> extendedInfoCall = MovieAPIFactory.getInstance().getAPIService().getExtendedInfo(slugId);
            emitter.setCancellable(() -> extendedInfoCall.cancel());
            extendedInfoCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    emitter.onNext(response.body());
                    emitter.onComplete();
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    emitter.onError(t);
                }
            });


        });*/
        view.showLoadingIndicator(false);
    }

}
