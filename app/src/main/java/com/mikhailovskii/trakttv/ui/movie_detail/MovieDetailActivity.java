package com.mikhailovskii.trakttv.ui.movie_detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.Movie;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;

public class MovieDetailActivity extends AppCompatActivity
        implements MovieDetailContract.MovieDetailView {

    private MovieDetailPresenter mPresenter = new MovieDetailPresenter();

    private TextView mDescription_tv;
    private TextView mCountry_tv;
    private TextView mRuntime_tv;
    private TextView mReleased_tv;
    private TextView mTagline_tv;
    private TextView mYear_tv;
    private TextView mToolbarTitle_tv;
    private TextView mNoInfo_tv;
    private ImageView mMovieImageView;
    private ImageButton mBack_btn;
    private FloatingActionButton mFloatingActionButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;

    private String mSlugId;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mPresenter.attachView(this);

        mSlugId = getIntent().getStringExtra(MovieListFragment.EXTRA_SLUG);
        mUrl = getIntent().getStringExtra(MovieListFragment.EXTRA_IMAGE);

        mDescription_tv = findViewById(R.id.description_textview);
        mCountry_tv = findViewById(R.id.country);
        mRuntime_tv = findViewById(R.id.runtime);
        mReleased_tv = findViewById(R.id.released);
        mTagline_tv = findViewById(R.id.tagline);
        mYear_tv = findViewById(R.id.year);
        mNoInfo_tv = findViewById(R.id.no_info);
        mMovieImageView = findViewById(R.id.movie_image);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle_tv = mToolbar.findViewById(R.id.toolbar_title);
        mBack_btn = mToolbar.findViewById(R.id.back);
        mFloatingActionButton = findViewById(R.id.favorite_button);
        mFloatingActionButton.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        mBack_btn.setOnClickListener(v -> onBackPressed());

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadMovieDetails(mSlugId));

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (mSlugId != null) {
            mPresenter.loadMovieDetails(mSlugId);
        }

    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {
        if (value) {
            mNoInfo_tv.setVisibility(View.VISIBLE);
        } else {
            mNoInfo_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        mSwipeRefreshLayout.setRefreshing(value);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMovieDetailsLoaded(@NonNull Movie movie) {
        mToolbarTitle_tv.setText(movie.getName());
        mDescription_tv.setText(movie.getOverview());
        mYear_tv.setText("Year: " + movie.getYear());
        mCountry_tv.setText("Country: " + movie.getCountry());
        mReleased_tv.setText("Released: " + movie.getReleased());
        mRuntime_tv.setText("Runtime: " + movie.getRuntime());
        mTagline_tv.setText("Tagline: " + movie.getTagline());

        Glide.with(this).load(mUrl).into(mMovieImageView);
    }

    @Override
    public void onMovieDetailsFailed() {

    }

}
