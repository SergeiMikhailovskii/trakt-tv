package com.mikhailovskii.trakttv.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie

import java.util.ArrayList

class MoviesAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val moviesList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        Glide.with(viewHolder.itemView.context)
                .load(movie.iconUrl)
                .into(viewHolder.mIconImageView)

        viewHolder.mMovieNameTextView.text = movie.name
        viewHolder.mYearTextView.text = viewHolder.itemView.context.resources.getString(R.string.year, movie.year)
        viewHolder.mWatchersTextView.text = viewHolder.itemView.context.resources.getString(R.string.watchers, movie.watchers)

        viewHolder.itemView.setOnClickListener { v ->
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(viewHolder.adapterPosition, movie)
            }
        }

        viewHolder.itemView.setOnLongClickListener { v ->
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemLongClick(viewHolder.adapterPosition, movie)
            }
            true
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setData(movies: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, item: Movie)

        fun onItemLongClick(position: Int, item: Movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mIconImageView: ImageView
        var mMovieNameTextView: TextView
        var mYearTextView: TextView
        var mWatchersTextView: TextView

        init {

            mIconImageView = itemView.findViewById(R.id.icon_image)
            mMovieNameTextView = itemView.findViewById(R.id.tv_moviename)
            mYearTextView = itemView.findViewById(R.id.tv_year)
            mWatchersTextView = itemView.findViewById(R.id.tv_watchers)
        }

    }

}
