package com.mikhailovskii.trakttv.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTrack {

    @SerializedName("movie")
    public Movie movie;
    @SerializedName("watchers")
    @Expose
    private int watchersNumber;

    public int getWatchersNumber() {
        return watchersNumber;
    }

    public void setWatchersNumber(int watchersNumber) {
        this.watchersNumber = watchersNumber;
    }

}
