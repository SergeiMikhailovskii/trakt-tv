package com.mikhailovskii.trakttv.ui.movie_detail;

import android.annotation.SuppressLint;
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
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity
        implements MovieDetailContract.MovieDetailView {

    private MovieDetailPresenter mPresenter = new MovieDetailPresenter();

    private TextView mDescription_tv;
    private TextView mCountry_tv;
    private TextView mRuntime_tv;
    private TextView mReleased_tv;
    private TextView mTagline_tv;
    private TextView mYear_tv;
    private ImageView mMovieImageView;
    private FloatingActionButton mFloatingActionButton;

    private String mSlugId;
    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mPresenter.attachView(this);

        mSlugId = getIntent().getStringExtra(MovieListFragment.EXTRA_SLUG);
        mUrl = getIntent().getStringExtra(MovieListFragment.EXTRA_IMAGE);
        mTitle = getIntent().getStringExtra(MovieListFragment.EXTRA_MOVIE);

        Objects.requireNonNull(getSupportActionBar()).setTitle(mTitle);

        mDescription_tv = findViewById(R.id.description_textview);
        mCountry_tv = findViewById(R.id.country);
        mRuntime_tv = findViewById(R.id.runtime);
        mReleased_tv = findViewById(R.id.released);
        mTagline_tv = findViewById(R.id.tagline);
        mYear_tv = findViewById(R.id.year);
        mMovieImageView = findViewById(R.id.movie_image);
        mFloatingActionButton = findViewById(R.id.favorite_button);
        mFloatingActionButton.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        if (mSlugId != null) {
            mPresenter.loadMovieDetails(mSlugId);
        }
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        Toast.makeText(this, "Empty details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMovieDetailsLoaded(@NonNull Movie movie) {
        mDescription_tv.setText(movie.getOverview());
        mYear_tv.setText("Year: "+movie.getYear());
        mCountry_tv.setText("Country: "+movie.getCountry());
        mReleased_tv.setText("Released: "+movie.getReleased());
        mRuntime_tv.setText("Runtime: "+movie.getRuntime());
        mTagline_tv.setText("Tagline: "+movie.getTagline());

        Glide.with(this).load(mUrl).into(mMovieImageView);
    }

    @Override
    public void onMovieDetailsFailed() {

    }

}
