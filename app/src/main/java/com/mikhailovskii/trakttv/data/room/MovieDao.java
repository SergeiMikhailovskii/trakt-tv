package com.mikhailovskii.trakttv.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface MovieDao {

    @Query("SELECT * from MovieEntity")
    List<MovieEntity> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovie(final MovieEntity movie);

    @Delete
    void deleteMovie(MovieEntity movie);

}
