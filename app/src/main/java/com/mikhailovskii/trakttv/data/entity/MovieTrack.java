package com.mikhailovskii.trakttv.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTrack {

    @SerializedName("watchers")
    @Expose
    private int watchersNumber;

    @SerializedName("movie")
    public Movie movie;

}
