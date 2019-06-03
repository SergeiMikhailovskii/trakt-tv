package com.mikhailovskii.trakttv.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.movies_list.MoviesListFragment;

public class MovieDetailActivity extends AppCompatActivity
        implements MovieDetailContract.MovieDetailView {

    private MovieDetailPresenter mPresenter = new MovieDetailPresenter();
    private TextView mDescriptionTextView;
    private ImageView mMovieImageView;
    private FloatingActionButton mFloatingActionButton;
    private String slugId;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mPresenter.attachView(this);

        slugId = getIntent().getStringExtra(MoviesListFragment.EXTRA_SLUG);
        url = getIntent().getStringExtra(MoviesListFragment.EXTRA_IMAGE);

        mDescriptionTextView = findViewById(R.id.description_textview);
        mMovieImageView = findViewById(R.id.movie_image);
        mFloatingActionButton = findViewById(R.id.favorite_button);
        mFloatingActionButton.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        if (slugId != null) {
            mPresenter.loadMovieDetails(slugId);
        }
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        // todo

    }

    @Override
    public void onMovieDetailsLoaded(Movie movie) {
        // mDescriptionTextView.setText(movie);

        Glide.with(this).load(url).into(mMovieImageView);
    }

    @Override
    public void onMovieDetailsFailed() {

    }
}
