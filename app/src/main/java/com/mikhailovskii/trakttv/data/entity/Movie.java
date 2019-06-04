package com.mikhailovskii.trakttv.data.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    private String iconUrl;
    private String slugId;
    private int watchers;

    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("ids")
    @Expose
    public MovieId movieId;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("released")
    @Expose
    private String released;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("overview")
    @Expose
    private String overview;

    public Movie(String iconUrl, String name, int year, String slugId, int watchers) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
        this.slugId = slugId;
        this.watchers = watchers;
    }

    public Movie(String iconUrl, String name, int year, String tagline, String released, int runtime, String country, String overview){
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
        this.tagline = tagline;
        this.released = released;
        this.runtime = runtime;
        this.country = country;
        this.overview = overview;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    public String getSlugId(){
        return slugId;
    }

    public void setSlugId(String slugId){
        this.slugId = slugId;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    @NonNull
    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @NonNull
    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
