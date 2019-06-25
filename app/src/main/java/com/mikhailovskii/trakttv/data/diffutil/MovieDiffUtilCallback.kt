package com.mikhailovskii.trakttv.data.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.mikhailovskii.trakttv.data.entity.Movie

class MovieDiffUtilCallback(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: Movie = oldList[oldItemPosition]
        val newMovie: Movie = newList[newItemPosition]
        return oldMovie.name == newMovie.name
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie: Movie = oldList[oldItemPosition]
        val newMovie: Movie = newList[newItemPosition]
        return oldMovie.name == newMovie.name
                && oldMovie.country == newMovie.country
                && oldMovie.overview == newMovie.overview
    }

}