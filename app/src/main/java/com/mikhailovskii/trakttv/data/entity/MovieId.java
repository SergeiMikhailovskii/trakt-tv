package com.mikhailovskii.trakttv.data.entity;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieId {

    @SerializedName("slug")
    @Expose
    private String slug;

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

}
