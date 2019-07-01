package com.mikhailovskii.trakttv.db.room

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.mikhailovskii.trakttv.data.entity.MovieId
import java.lang.Exception

public class RoomTypeConverter {

    @TypeConverter
    fun movieIdToString(movieId: MovieId): String {
        return Gson().toJson(movieId)
    }

    @TypeConverter
    fun stringToMovieId(data: String): MovieId? {
        return try {
            Gson().fromJson(data, MovieId::class.java)
        } catch (ex: Exception) {
            null
        }
    }

}
