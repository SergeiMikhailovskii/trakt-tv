package com.mikhailovskii.trakttv.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.mikhailovskii.trakttv.data.entity.Movie

import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {

    @get:Query("SELECT * from Movie")
    val favorites: Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie): Completable

    @Query("DELETE FROM Movie WHERE name = :name")
    fun deleteMovie(name: String): Completable

}
