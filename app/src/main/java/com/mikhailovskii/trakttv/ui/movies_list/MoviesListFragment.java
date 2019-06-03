package com.mikhailovskii.trakttv.ui.movies_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.adapter.MoviesRecyclerAdapter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;

import java.util.List;


public class MoviesListFragment extends Fragment
        implements MoviesListContract.MoviesListView {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_SLUG = "EXTRA_SLUG";

    private MoviesListPresenter mPresenter = new MoviesListPresenter();
    private RecyclerView mMoviesRecycler;
    private MoviesRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private List<Movie> mMoviesList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.attachView(this);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mMoviesRecycler = view.findViewById(R.id.movies_list);
        mMoviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        mPresenter.loadMovieList();

        return view;
    }

    @Override
    public void onMovieListLoaded(List<Movie> movieList) {
        mProgressBar.setVisibility(View.GONE);
        mMoviesList = movieList;

        // todo
        mAdapter = new MoviesRecyclerAdapter(mMoviesList, getContext());

        //Handle click on item
        mAdapter.setOnItemClickListener((position, item) -> {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE, item.getName());
            intent.putExtra(EXTRA_IMAGE, item.getIconUrl());
            intent.putExtra(EXTRA_SLUG, item.getSlugId());
            startActivity(intent);
        });

        mMoviesRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onMovieListFailed() {
        Toast.makeText(getContext(), "Loading failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        Toast.makeText(getContext(), "List is empty", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

}