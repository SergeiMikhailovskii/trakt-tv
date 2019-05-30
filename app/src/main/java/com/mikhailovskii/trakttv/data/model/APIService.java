package com.mikhailovskii.trakttv.data.model;

import com.mikhailovskii.trakttv.ui.movies_list.MoviesListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

/*    @GET("/authorize?response_type=code&client_id=9b36d8c0db59eff5038aea7a417d73e69aea75b41aac771816d2ef1b3109cc2f&redirect_uri=urn:ietf:wg:oauth:2.0:oob")
    Call<Integer> authorize();*/

    @GET("/movies/trending")
    Call<List<MoviesListResponse>> getMovies();

}
