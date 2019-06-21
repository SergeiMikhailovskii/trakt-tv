package com.mikhailovskii.trakttv.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mikhailovskii.trakttv.db.room.RoomTypeConverter

@Entity
data class Movie(

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @SerializedName("ids")
        @Expose
        var movieId: MovieId? = null,

        @SerializedName("title")
        @Expose
        var name: String? = null,

        @SerializedName("year")
        @Expose
        var year: Int = 0,

        @SerializedName("tagline")
        @Expose
        var tagline: String? = null,

        @SerializedName("released")
        @Expose
        var released: String? = null,

        @SerializedName("runtime")
        @Expose
        var runtime: Int = 0,

        @SerializedName("country")
        @Expose
        var country: String? = null,

        @SerializedName("overview")
        @Expose
        var overview: String? = null,

        var iconUrl: String? = null,

        var isFavorite: Boolean = false,

        @SerializedName("watchers")
        @Expose
        var watchers: Int? = null

        /* constructor()

         //For movie list
         @Ignore
         constructor(iconUrl: String, name: String, year: Int, slugId: String, watchers: Int) {
             this.iconUrl = iconUrl
             this.name = name
             this.year = year
             this.movieId?.slug = slugId
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
             this.movieId?.slug = slugId
             this.watchers = watchers
         }

         //For room
         constructor(name: String, watchers: Int, iconUrl: String, slugId: String) {
             this.iconUrl = iconUrl
             this.movieId?.slug = slugId
             this.watchers = watchers
             this.name = name
         }*/

)
