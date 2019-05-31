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
import com.mikhailovskii.trakttv.ui.movies_list.MoviesListFragment;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.MovieDetailView {

    private TextView mDescriptionTextView;
    private ImageView mMovieImageView;
    private FloatingActionButton mFloatingActionButton;

    private MovieDetailPresenter mPresenter = new MovieDetailPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPresenter.attachView(this);

        mDescriptionTextView = findViewById(R.id.description_textview);
        mMovieImageView = findViewById(R.id.movie_image);
        mFloatingActionButton = findViewById(R.id.favorite_button);

        mFloatingActionButton.setOnClickListener(view -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show());

        mDescriptionTextView.setText(getIntent().getStringExtra(MoviesListFragment.EXTRA_MOVIE));
        Glide.with(this)
                .load(getIntent().getStringExtra(MoviesListFragment.EXTRA_IMAGE))
                .into(mMovieImageView);

        mPresenter.getExtendedInfo();


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

    @Override
    public void onExtendedInfoGetSuccess() {

    }
}
