package com.mikhailovskii.trakttv;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.facebook.stetho.Stetho;
import com.mikhailovskii.trakttv.data.room.MovieDatabase;

public class TraktTvApp extends Application {

    private static Context sAppContext;
    public static TraktTvApp instance;
    private MovieDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        instance = this;
        database = Room.databaseBuilder(this, MovieDatabase.class, "MovieDatabase").build();
        Stetho.initializeWithDefaults(this);
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }

    public static TraktTvApp getInstance() {
        return instance;
    }

    public MovieDatabase getDatabase() {
        return database;
    }

}
