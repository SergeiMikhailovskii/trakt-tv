package com.mikhailovskii.trakttv.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mikhailovskii.trakttv.db.room.MovieIdConverter;

@Entity
public class Movie {

    private int id;
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
    @TypeConverters({MovieIdConverter.class})
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

    public Movie(@NonNull String iconUrl, @NonNull String name, int year, @NonNull String slugId, int watchers) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
        this.slugId = slugId;
        this.watchers = watchers;
    }

    public Movie(@NonNull String iconUrl, @NonNull String name, int year, @NonNull String tagline, @NonNull String released, int runtime, @NonNull String country, @NonNull String overview, @NonNull String slugId, int watchers) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.year = year;
        this.tagline = tagline;
        this.released = released;
        this.runtime = runtime;
        this.country = country;
        this.overview = overview;
        this.slugId = slugId;
        this.watchers = watchers;
    }

    @NonNull
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(@NonNull String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    public String getSlugId() {
        return slugId;
    }

    public void setSlugId(@NonNull String slugId) {
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

    public void setTagline(@NonNull String tagline) {
        this.tagline = tagline;
    }

    @NonNull
    public String getReleased() {
        return released;
    }

    public void setReleased(@NonNull String released) {
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

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    public void setOverview(@NonNull String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
