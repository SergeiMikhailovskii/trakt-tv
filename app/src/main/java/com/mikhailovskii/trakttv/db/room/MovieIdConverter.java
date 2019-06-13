package com.mikhailovskii.trakttv.db.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.mikhailovskii.trakttv.data.entity.MovieId;

public class MovieIdConverter {

    @TypeConverter
    public String fromId(MovieId movieId){
        return new Gson().toJson(movieId);
    }

    @TypeConverter
    public MovieId toId(String data){
        return new Gson().fromJson(data, MovieId.class);
    }

}
