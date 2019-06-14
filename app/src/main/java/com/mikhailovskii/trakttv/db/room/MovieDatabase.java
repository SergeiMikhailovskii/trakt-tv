package com.mikhailovskii.trakttv.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.entity.Movie;

@Database(entities = {Movie.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    public static MovieDao getMovieDao() {
        return TraktTvApp.getInstance().getDatabase().movieDao();
    }

    public abstract MovieDao movieDao();

}
