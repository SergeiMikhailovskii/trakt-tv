package com.mikhailovskii.trakttv.ui.movies_list;

import android.util.Log;

import com.mikhailovskii.trakttv.data.model.Movie;
import com.mikhailovskii.trakttv.data.model.NetworkService;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListPresenter extends BasePresenter<MoviesListContract.MoviesListView>
        implements MoviesListContract.MoviesListPresenter {

    @Override
    public void downloadMoviesList() {
        Log.i(MoviesListFragment.TAG, "In download movies list");
        List<Movie> list = new ArrayList<>();
/*
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 1", "Motto 1"));
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 2", "Motto 2"));
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 3", "Motto 3"));
*/



/*        Call<Integer> authorizeCall = NetworkService.getInstance().getAPIService().authorize();
        authorizeCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i("ResCode", response.code()+"");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });*/

        Call<List<MoviesListResponse>> moviesListCall = NetworkService.getInstance().getAPIService().getMovies();
        moviesListCall.enqueue(new Callback<List<MoviesListResponse>>() {
            @Override
            public void onResponse(Call<List<MoviesListResponse>> call, Response<List<MoviesListResponse>> response) {
                Log.i("ResCode", response.code() + "");
                view.onMoviesListDownloadSuccess(list);
            }

            @Override
            public void onFailure(Call<List<MoviesListResponse>> call, Throwable t) {
                Log.i("ResCode", "Download failed");
            }
        });



    }

    @Override
    public void downloadMovieInfo() {

    }

}
