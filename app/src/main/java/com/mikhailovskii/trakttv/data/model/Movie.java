package com.mikhailovskii.trakttv.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Movie {

    public String iconUrl;

    @SerializedName("title")
    public String name;

    @SerializedName("year")
    public String year;

    @SerializedName("ids")
    public MovieIDS movieIDS;

    public Movie(String iconUrl, String name, String year) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
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

}
