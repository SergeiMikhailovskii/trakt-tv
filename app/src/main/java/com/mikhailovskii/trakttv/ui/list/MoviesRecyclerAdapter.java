package com.mikhailovskii.trakttv.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhailovskii.trakttv.R;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> moviesList;

    private LayoutInflater layoutInflater;

    private OnItemClickListener<Movie> onItemClickListener;


    public MoviesRecyclerAdapter(List<Movie> moviesList, Context context){
        this.moviesList = moviesList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = layoutInflater.inflate(R.layout.movie_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){

            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mIconImageView;
        public TextView mShowNametextView;
        public TextView mMottoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIconImageView = itemView.findViewById(R.id.icon_imageview);
            mShowNametextView = itemView.findViewById(R.id.showname_textview);
            mMottoTextView = itemView.findViewById(R.id.motto_textview);

        }

    }


    public interface OnItemClickListener<T>{
        void onItemClicked(int position, T item);
    }

}
