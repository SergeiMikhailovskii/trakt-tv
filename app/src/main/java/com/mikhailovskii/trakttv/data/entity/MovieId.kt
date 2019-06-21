package com.mikhailovskii.trakttv.data.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieId {

    @SerializedName("slug")
    @Expose
    var slug: String? = null

}
