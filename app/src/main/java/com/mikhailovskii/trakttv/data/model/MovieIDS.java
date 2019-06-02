package com.mikhailovskii.trakttv.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieIDS {

    @SerializedName("slug")
    @Expose
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
