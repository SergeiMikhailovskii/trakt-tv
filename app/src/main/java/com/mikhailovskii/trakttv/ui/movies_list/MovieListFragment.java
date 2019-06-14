package com.mikhailovskii.trakttv.ui.movies_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MovieListFragment extends Fragment implements MovieListContract.MoviesListView,
        MoviesAdapter.OnItemClickListener {

    public static final String FRAGMENT_NAME = "List";

    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_SLUG = "EXTRA_SLUG";

    private MovieListPresenter mPresenter = new MovieListPresenter();
    private SwipeRefreshLayout mSwipeRefresh;
    private TextView mTvNoFilms;
    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.attachView(this);

        mTvNoFilms = view.findViewById(R.id.no_films);

        RecyclerView moviesRecycler = view.findViewById(R.id.movies_list);
        moviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        moviesRecycler.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(() -> {
            mPresenter.loadMovieList();
            mSwipeRefresh.setRefreshing(false);
        });

        mAdapter = new MoviesAdapter(this);
        moviesRecycler.setAdapter(mAdapter);

        mPresenter.loadMovieList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onItemClicked(int position, Movie item) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, item.getIconUrl());
        intent.putExtra(EXTRA_SLUG, item.getSlugId());
        intent.putExtra(MovieDetailActivity.PREV_ACTIVITY, FRAGMENT_NAME);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, Movie item) {
        // not used yet
    }

    @Override
    public void onMovieListLoaded(@NonNull @NotNull List<Movie> movieList) {
        mAdapter.setData(movieList);
    }

    @Override
    public void onMovieListFailed() {
        Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        if (value) {
            mTvNoFilms.setVisibility(View.VISIBLE);
        } else {
            mTvNoFilms.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        mSwipeRefresh.setRefreshing(value);
    }

}