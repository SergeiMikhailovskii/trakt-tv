package com.mikhailovskii.trakttv.db.room

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.mikhailovskii.trakttv.data.entity.MovieId

class MovieIdConverter {

    @TypeConverter
    fun fromId(movieId: MovieId): String {
        return Gson().toJson(movieId)
    }

    @TypeConverter
    fun toId(data: String): MovieId {
        return Gson().fromJson(data, MovieId::class.java!!)
    }

}
