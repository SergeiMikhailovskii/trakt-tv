package com.mikhailovskii.trakttv.data.api;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("/oauth/authorize?response_type=code&client_id=a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b&redirect_uri=urn:ietf:wg:oauth:2.0:oob")
    Call<Integer> authorize();

    @GET("/movies/trending")
    Call<List<MovieTrack>> getMovies();

    @GET("/movies/{slug}?extended=full")
    Call<Movie> getExtendedInfo(@Path("slug") String slug);

    //PIN: EDB775B0
    @GET("/oauth/device/code")
    Call<Integer> getCode();

}
