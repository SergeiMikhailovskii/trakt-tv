package com.mikhailovskii.trakttv.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mikhailovskii.trakttv.db.room.MovieIdConverter

@Entity
class Movie {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("ids")
    @Expose
    @TypeConverters(MovieIdConverter::class)
    var movieId: MovieId = MovieId()

    @SerializedName("title")
    @Expose
    var name: String? = null

    @SerializedName("year")
    @Expose
    var year: Int = 0

    @SerializedName("tagline")
    @Expose
    @Ignore
    var tagline: String? = null

    @SerializedName("released")
    @Expose
    @Ignore
    var released: String? = null

    @SerializedName("runtime")
    @Expose
    @Ignore
    var runtime: Int = 0

    @SerializedName("country")
    @Expose
    @Ignore
    var country: String? = null

    @SerializedName("overview")
    @Expose
    @Ignore
    var overview: String? = null

    var iconUrl: String? = null

    @SerializedName("watchers")
    @Expose
    var watchers: Int = 0

    constructor()

    //For movie list
    @Ignore
    constructor(iconUrl: String, name: String, year: Int, slugId: String, watchers: Int) {
        this.iconUrl = iconUrl
        this.name = name
        this.year = year
        this.movieId.slug = slugId
        this.watchers = watchers
    }

    //For movie detail
    @Ignore
    constructor(iconUrl: String, name: String, year: Int, tagline: String, released: String, runtime: Int, country: String, overview: String, slugId: String, watchers: Int) {
        this.iconUrl = iconUrl
        this.name = name
        this.year = year
        this.tagline = tagline
        this.released = released
        this.runtime = runtime
        this.country = country
        this.overview = overview
        this.movieId.slug = slugId
        this.watchers = watchers
    }

    //For room
    constructor(name: String, watchers: Int, iconUrl: String, slugId: String) {
        this.iconUrl = iconUrl
        this.movieId.slug = slugId
        this.watchers = watchers
        this.name = name
    }

}
