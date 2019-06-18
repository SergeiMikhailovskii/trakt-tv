package com.mikhailovskii.trakttv.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieTrack {

    @SerializedName("movie")
    var movie: Movie? = null
    @SerializedName("watchers")
    @Expose
    var watchersNumber: Int = 0

}
