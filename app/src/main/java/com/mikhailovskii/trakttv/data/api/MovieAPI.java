package com.mikhailovskii.trakttv.data.api;

import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.data.entity.MovieTrack;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("/movies/trending")
    @NonNull
    Observable<List<MovieTrack>> getMovies();

    @GET("/movies/{slug}?extended=full")
    @NonNull
    Observable<Movie> getExtendedInfo(@Path("slug") String slug);

}