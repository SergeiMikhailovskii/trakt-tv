package com.mikhailovskii.trakttv.ui.movies_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieListFragment extends Fragment implements MovieListContract.MoviesListView,
        MoviesAdapter.OnItemClickListener {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_SLUG = "EXTRA_SLUG";

    private MovieListPresenter mPresenter = new MovieListPresenter();
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMoviesRecycler;
    private TextView mNoFilms;
    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.attachView(this);

        mNoFilms = view.findViewById(R.id.no_films);

        mMoviesRecycler = view.findViewById(R.id.movies_list);
        mMoviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMoviesRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(() -> {
            mPresenter.loadMovieList();
            mSwipeRefresh.setRefreshing(false);
        });

        mAdapter = new MoviesAdapter(this);
        mMoviesRecycler.setAdapter(mAdapter);

        mPresenter.loadMovieList();

        return view;
    }

    @Override
    public void onItemClicked(int position, Movie item) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, item.getName());
        intent.putExtra(EXTRA_IMAGE, item.getIconUrl());
        intent.putExtra(EXTRA_SLUG, item.getSlugId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, Movie item) {
        // not used yet
    }

    @Override
    public void onMovieListLoaded(List<Movie> movieList) {
        mAdapter.setData(movieList);
    }

    @Override
    public void onMovieListFailed() {
        Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        if (value){
            mNoFilms.setVisibility(View.VISIBLE);
        } else  {
            mNoFilms.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        mSwipeRefresh.setRefreshing(value);
    }

}