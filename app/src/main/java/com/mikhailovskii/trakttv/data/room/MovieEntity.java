package com.mikhailovskii.trakttv.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int watchers;
    private String iconUrl;

    public MovieEntity(String name, int watchers, String iconUrl) {
        this.name = name;
        this.watchers = watchers;
        this.iconUrl = iconUrl;
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

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
