package com.mikhailovskii.trakttv.data.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    private String iconUrl;

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("ids")
    @Expose
    public MovieId movieId;

    private String slugId;

    public Movie(String iconUrl, String name, String year, String slugId) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
        this.slugId = slugId;
    }

    @NonNull
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @NonNull
    public String getSlugId(){
        return slugId;
    }

    public void setSlugId(String slugId){
        this.slugId = slugId;
    }

}
