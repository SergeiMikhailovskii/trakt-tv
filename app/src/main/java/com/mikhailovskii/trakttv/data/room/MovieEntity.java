package com.mikhailovskii.trakttv.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "watchers")
    private int watchers;
    @ColumnInfo(name = "icon")
    private String iconUrl;

    @ColumnInfo(name = "slug")
    private String slug;

    public MovieEntity(String name, int watchers, String iconUrl, String slug) {
        this.name = name;
        this.watchers = watchers;
        this.iconUrl = iconUrl;
        this.slug = slug;
    }

    @NonNull
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(@NonNull String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
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

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

}
