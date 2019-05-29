package com.mikhailovskii.trakttv.ui.movies_list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.model.Movie;

import java.util.List;


public class MoviesListFragment extends Fragment
        implements MoviesListContract.MoviesListView {

    private MoviesListPresenter mPresenter = new MoviesListPresenter();

    private RecyclerView mMoviesRecycler;

    private MoviesRecyclerAdapter adapter;

    private List<Movie> moviesList;
    public static final String TAG = "HELLO_IT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.attachView(this);
        Log.i(TAG, "In onCreate MLF");
        mMoviesRecycler = view.findViewById(R.id.movies_list);
        mMoviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.downloadMoviesList();


        return view;
    }

    @Override
    public void onMoviesListDownloadSuccess(List<Movie> movieList) {
        moviesList = movieList;
        Log.i(TAG, "in on movies list download success");
        adapter = new MoviesRecyclerAdapter(moviesList, getContext());
        adapter.setOnItemClickListener((position, item) -> Toast.makeText(getContext(), "Movie " + item.getName() + "clicked!", Toast.LENGTH_SHORT).show());
        mMoviesRecycler.setAdapter(adapter);
    }

    @Override
    public void onMovieInfoDownloadSuccess() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}