package com.mikhailovskii.trakttv.ui.movies_list;

import android.os.AsyncTask;
import android.util.Log;

import com.mikhailovskii.trakttv.data.model.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesListDownload extends AsyncTask<Void, Void, ArrayList<Movie>> {

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        ArrayList<Movie> moviesList = new ArrayList<>();

        String baseUrl = "https://api.trakt.tv/";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("trakt-api-version", "2")
                    .header("trakt-api-key", "a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b");



            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit retrofit = builder.build();

        APIService apiService = retrofit.create(APIService.class);
        Call<List<Movie>> moviesListCall = apiService.getMovies();

        try {
            Response<List<Movie>> result = moviesListCall.execute();
            Log.i("ResCode",result.code()+"");
            if (result.code() == 200) {
                moviesList.addAll(Objects.requireNonNull(result.body()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return moviesList;
    }

}
