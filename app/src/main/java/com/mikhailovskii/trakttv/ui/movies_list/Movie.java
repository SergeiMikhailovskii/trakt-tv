package com.mikhailovskii.trakttv.ui.movies_list;

import android.support.annotation.NonNull;

public class Movie {

    private String iconUrl;
    private String name;
    private String motto;

    public Movie(String iconUrl, String name, String motto) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.motto = motto;
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

    @NonNull
    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

}
