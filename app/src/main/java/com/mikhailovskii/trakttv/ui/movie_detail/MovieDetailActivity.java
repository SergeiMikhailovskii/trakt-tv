package com.mikhailovskii.trakttv.ui.movie_detail;

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

    private TextView mTvDescription;
    private TextView mTvCountry;
    private TextView mTvRuntime;
    private TextView mTvReleased;
    private TextView mTvTagline;
    private TextView mTvYear;
    private TextView mTvToolbarTitle;
    private TextView mTvNoInfo;
    private ImageView mIvMovie;
    private ImageButton mBtnBack;
    private FloatingActionButton mFabFavorite;
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

        mTvDescription = findViewById(R.id.tv_description);
        mTvCountry = findViewById(R.id.tv_country);
        mTvRuntime = findViewById(R.id.tv_runtime);
        mTvReleased = findViewById(R.id.tv_released);
        mTvTagline = findViewById(R.id.tv_tagline);
        mTvYear = findViewById(R.id.tv_year);
        mTvNoInfo = findViewById(R.id.tv_no_info);
        mIvMovie = findViewById(R.id.movie_image);
        mToolbar = findViewById(R.id.toolbar);
        mTvToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mBtnBack = mToolbar.findViewById(R.id.back);
        mFabFavorite = findViewById(R.id.favorite);
        mFabFavorite.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        mBtnBack.setOnClickListener(v -> onBackPressed());

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
            mTvNoInfo.setVisibility(View.VISIBLE);
        } else {
            mTvNoInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {
        mSwipeRefreshLayout.setRefreshing(value);
    }

    @Override
    public void onMovieDetailsLoaded(@NonNull Movie movie) {
        mTvToolbarTitle.setText(movie.getName());
        mTvDescription.setText(movie.getOverview());
        mTvYear.setText(getString(R.string.year, movie.getYear()));
        mTvCountry.setText(getString(R.string.country, movie.getCountry()));
        mTvReleased.setText(getString(R.string.released, movie.getReleased()));
        mTvRuntime.setText(getString(R.string.runtime, movie.getRuntime()));
        mTvTagline.setText(getString(R.string.tagline, movie.getTagline()));


        Glide.with(this).load(mUrl).into(mIvMovie);
    }

    @Override
    public void onMovieDetailsFailed() {

    }

}
