package com.mikhailovskii.trakttv.ui.movies_list;

import com.google.gson.annotations.SerializedName;
import com.mikhailovskii.trakttv.data.model.Movie;

public class MoviesListResponse {

    @SerializedName("movie")
    private Movie movie;

}
