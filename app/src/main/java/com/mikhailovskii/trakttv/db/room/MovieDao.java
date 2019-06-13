package com.mikhailovskii.trakttv.db.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mikhailovskii.trakttv.data.entity.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * from Movie")
    Single<List<Movie>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovie(final Movie movie);

/*    @Delete
    Completable deleteMovie(Movie movie);*/

    @Query("DELETE FROM Movie WHERE name = :name")
    Completable deleteMovie(String name);

}
