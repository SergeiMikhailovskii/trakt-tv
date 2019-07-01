package com.mikhailovskii.trakttv.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
        var watchers: Int = 0

)
