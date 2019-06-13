package com.mikhailovskii.trakttv.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mikhailovskii.trakttv.TraktTvApp;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public static MovieDao getMovieDao(){
        return TraktTvApp.getInstance().getDatabase().movieDao();
    }

}
