package com.mikhailovskii.trakttv.data.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieId(

        @SerializedName("slug")
        @Expose
        var slug: String? = null,

        @SerializedName("imdb")
        @Expose
        var imdb: String? = null

)
