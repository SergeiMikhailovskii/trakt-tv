package com.mikhailovskii.trakttv.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> moviesList;

    private LayoutInflater layoutInflater;

    private OnItemClickListener<Movie> onItemClickListener;

    private Context context;


    public MoviesRecyclerAdapter(List<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = layoutInflater.inflate(R.layout.movie_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                fireItemClicked(position, moviesList.get(position));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = moviesList.get(i);
        Glide.with(context)
                .load(movie.getIconUrl())
                .into(viewHolder.mIconImageView);
        viewHolder.mMovieNameTextView.setText(movie.getName());
        viewHolder.mYearTextView.setText("Year: " + movie.getYear());
        viewHolder.mWatchersTextView.setText("Watchers: " + movie.getWatchers());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void fireItemClicked(int position, Movie movie) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClicked(position, movie);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<Movie> listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener<T> {
        void onItemClicked(int position, T item);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIconImageView;
        public TextView mMovieNameTextView;
        public TextView mYearTextView;
        public TextView mWatchersTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIconImageView = itemView.findViewById(R.id.icon_imageview);
            mMovieNameTextView = itemView.findViewById(R.id.moviename_textview);
            mYearTextView = itemView.findViewById(R.id.year_textview);
            mWatchersTextView = itemView.findViewById(R.id.watchers);

        }

    }

}
