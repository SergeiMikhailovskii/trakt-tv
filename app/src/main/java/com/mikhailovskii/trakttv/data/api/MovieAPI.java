package com.mikhailovskii.trakttv.data.api;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("/movies/trending")
    Call<List<MovieTrack>> getMovies();

    Observable<List<MovieTrack>> getMovies1();

    @GET("/movies/{slug}?extended=full")
    Call<Movie> getExtendedInfo(@Path("slug") String slug);

}