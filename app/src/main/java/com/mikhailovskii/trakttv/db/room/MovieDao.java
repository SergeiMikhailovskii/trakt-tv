package com.mikhailovskii.trakttv.db.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * from MovieEntity")
    Single<List<MovieEntity>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovie(final MovieEntity movie);

    @Delete
    Completable deleteMovie(MovieEntity movie);

}
