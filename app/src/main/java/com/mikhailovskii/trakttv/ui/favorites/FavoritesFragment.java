package com.mikhailovskii.trakttv.ui.favorites;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import org.jetbrains.annotations.NotNull;

public class FavoritesFragment extends Fragment
        implements FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    private FavoritesPresenter mPresenter = new FavoritesPresenter();
    private TextView mTvNoFilms;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mMoviesRecycler;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mPresenter.attachView(this);

        mTvNoFilms = view.findViewById(R.id.no_films);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mMoviesRecycler = view.findViewById(R.id.movies_list);
        mMoviesRecycler.setAdapter(new MoviesAdapter(this));

        mPresenter.loadFavoriteMovies();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onMoviesLoaded() {
        showEmptyState(false);
    }

    @Override
    public void onMoviesFailed() {
        Toast.makeText(getContext(), getString(R.string.loading_failed), Toast.LENGTH_SHORT).show();
        showEmptyState(true);
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

    }

    @Override
    public void onItemLongClick(int position, Movie item) {

    }
}

