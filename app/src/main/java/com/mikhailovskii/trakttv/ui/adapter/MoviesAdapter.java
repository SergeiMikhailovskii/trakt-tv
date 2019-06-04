package com.mikhailovskii.trakttv.ui.adapter;

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

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> moviesList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_element, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie movie = moviesList.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(movie.getIconUrl())
                .into(viewHolder.mIconImageView);

        viewHolder.mMovieNameTextView.setText(movie.getName());
        viewHolder.mYearTextView.setText("Year: " + movie.getYear());
        viewHolder.mWatchersTextView.setText("Watchers: " + movie.getWatchers());

        viewHolder.itemView.setOnClickListener(v -> {
            if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(viewHolder.getAdapterPosition(), movie);
            }
        });

        viewHolder.itemView.setOnLongClickListener(v -> {
            if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemLongClick(viewHolder.getAdapterPosition(), movie);
            }
            return false;
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setData(List<Movie> movies) {
        moviesList.clear();
        moviesList.addAll(movies);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, Movie item);

        void onItemLongClick(int position, Movie item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIconImageView;
        TextView mMovieNameTextView;
        TextView mYearTextView;
        TextView mWatchersTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIconImageView = itemView.findViewById(R.id.icon_imageview);
            mMovieNameTextView = itemView.findViewById(R.id.moviename_textview);
            mYearTextView = itemView.findViewById(R.id.year_textview);
            mWatchersTextView = itemView.findViewById(R.id.watchers);
        }

    }

}
