package com.mikhailovskii.trakttv.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import kotlinx.android.synthetic.main.movie_element.view.*
import java.util.*

class MoviesAdapter(
        private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(movieList[position], onItemClickListener)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setData(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClicked(position: Int, item: Movie)

        fun onItemLongClick(position: Int, item: Movie)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(movie: Movie, onItemClickListener: OnItemClickListener) {
            Glide.with(itemView.context)
                    .load(movie.iconUrl)
                    .into(itemView.icon_image)

            itemView.tv_moviename.text = movie.name
            itemView.tv_year.text = itemView.context.resources.getString(R.string.year, movie.year)
            itemView.tv_watchers.text = itemView.context.resources.getString(R.string.watchers, movie.watchers)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(adapterPosition, movie)
                }
            }

            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemLongClick(adapterPosition, movie)
                }
                true
            }
        }

    }

}
