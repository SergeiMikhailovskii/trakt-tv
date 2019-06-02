package com.mikhailovskii.trakttv.ui.movie_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("overview")
    @Expose
    private String overview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
