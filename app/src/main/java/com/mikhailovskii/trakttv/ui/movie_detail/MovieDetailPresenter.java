package com.mikhailovskii.trakttv.ui.movie_detail;

import android.util.Log;

import com.mikhailovskii.trakttv.data.model.Movie;
import com.mikhailovskii.trakttv.data.model.NetworkService;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {


    @Override
    public void getExtendedInfo() {
        Call<Movie> extendedInfoCall = NetworkService.getInstance().getAPIService().getExtendedInfo("captain-marvel-2019");
        extendedInfoCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.i("Rescode", String.valueOf(response.code()));
                view.onExtendedInfoGetSuccess();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

}
