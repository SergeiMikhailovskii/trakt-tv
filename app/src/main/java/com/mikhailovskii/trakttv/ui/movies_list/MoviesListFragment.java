package com.mikhailovskii.trakttv.ui.movies_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.model.Movie;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;

import java.util.List;


public class MoviesListFragment extends Fragment
        implements MoviesListContract.MoviesListView {

    private MoviesListPresenter mPresenter = new MoviesListPresenter();

    private RecyclerView mMoviesRecycler;

    private MoviesRecyclerAdapter adapter;

    private List<Movie> moviesList;


    public static final String TAG = "DebugTag";

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

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
        adapter.setOnItemClickListener((position, item) -> {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE, item.getName());
            intent.putExtra(EXTRA_IMAGE, item.getIconUrl());
            startActivity(intent);
        });
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