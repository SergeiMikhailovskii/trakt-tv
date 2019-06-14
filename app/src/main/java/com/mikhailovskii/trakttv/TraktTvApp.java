package com.mikhailovskii.trakttv;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.facebook.stetho.Stetho;
import com.mikhailovskii.trakttv.db.room.MovieDatabase;

public class TraktTvApp extends Application {

    public static TraktTvApp instance;

    private static String DB_NAME = "MovieDatabase";
    private static Context sAppContext;
    private MovieDatabase database;

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }

    public static TraktTvApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        instance = this;
        database = Room.databaseBuilder(this, MovieDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
        setupDebugTools();
    }

    public MovieDatabase getDatabase() {
        return database;
    }

    private void setupDebugTools() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

}
