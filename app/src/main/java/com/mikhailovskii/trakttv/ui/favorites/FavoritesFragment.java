package com.mikhailovskii.trakttv.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter;
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class FavoritesFragment extends Fragment
        implements FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    private FavoritesPresenter mPresenter = new FavoritesPresenter();
    private TextView mTvNoFilms;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMoviesRecycler;
    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mPresenter.attachView(this);

        mTvNoFilms = view.findViewById(R.id.no_films);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadFavoriteMovies());

        mMoviesRecycler = view.findViewById(R.id.movies_list);
        mMoviesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMoviesRecycler.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        mAdapter = new MoviesAdapter(this);
        mMoviesRecycler.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFavoriteMovies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onMoviesLoaded(List<Movie> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onMoviesFailed() {
        Toast.makeText(getContext(), getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
        showEmptyState(true);
    }

    @Override
    public void onMovieDeleted() {
        Toast.makeText(getContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieDeleteFailed() {

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

    @Override
    public void onItemClicked(int position, Movie item) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieListFragment.EXTRA_IMAGE, item.getIconUrl());
        intent.putExtra(MovieListFragment.EXTRA_SLUG, item.getSlugId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, Movie item) {
        Bundle bundle = new Bundle();
        bundle.putString(MovieDetailActivity.EXTRA_NAME, item.getName());
        bundle.putInt(MovieDetailActivity.EXTRA_WATCHERS, item.getWatchers());
        bundle.putString(MovieListFragment.EXTRA_SLUG, item.getSlugId());
        bundle.putString(MovieListFragment.EXTRA_IMAGE, item.getIconUrl());
        mPresenter.deleteMovie(bundle);
    }

}

