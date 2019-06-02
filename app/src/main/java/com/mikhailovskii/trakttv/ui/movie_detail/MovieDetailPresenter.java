package com.mikhailovskii.trakttv.ui.movie_detail;

import android.util.Log;

import com.mikhailovskii.trakttv.data.model.NetworkService;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.MovieDetailView>
        implements MovieDetailContract.MovieDetailPresenter {


    @Override
    public void getExtendedInfo(String slugId) {
        Call<MovieDetailResponse> extendedInfoCall = NetworkService.getInstance().getAPIService().getExtendedInfo(slugId);
        extendedInfoCall.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                Log.i("Rescode", String.valueOf(response.code()));

                String overview = response.body().getOverview();

                view.onExtendedInfoGetSuccess(overview);
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

            }
        });
    }

}
