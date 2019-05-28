package com.mikhailovskii.trakttv.ui.movies_list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhailovskii.trakttv.R;


public class MoviesListFragment extends Fragment
        implements MoviesListContract.MoviesListView {

    private MoviesListPresenter mPresenter = new MoviesListPresenter();

    private RecyclerView mMoviesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mPresenter.attachView(this);

        mMoviesRecycler = view.findViewById(R.id.movies_list);

        return view;
    }

    @Override
    public void onMoviesListDownloadSuccess() {

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
