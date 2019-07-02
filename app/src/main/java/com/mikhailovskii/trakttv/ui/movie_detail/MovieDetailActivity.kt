package com.mikhailovskii.trakttv.ui.movie_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.toast

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.MovieDetailView {

    private val presenter = MovieDetailPresenter()

    private var slugId: String? = null
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        presenter.attachView(this)

        setToolbar()

        slugId = intent.getStringExtra(MovieListFragment.EXTRA_SLUG)

        swipe_refresh.setOnRefreshListener {
            presenter.loadMovieDetails(slugId)
        }

        btn_favorite.setOnClickListener {
            movie?.let {
                if (it.isFavorite) {
                    presenter.removeMovieFromFavorites(it.name!!)
                    btn_favorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    presenter.addMovieToFavorites(it)
                    btn_favorite.setImageResource(R.drawable.ic_remove)
                }
            }
        }

        presenter.loadMovieDetails(slugId)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            tv_no_info.visibility = View.VISIBLE
        } else {
            tv_no_info.visibility = View.GONE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        swipe_refresh.isRefreshing = value
    }

    override fun onMovieDetailsLoaded(movie: Movie) {
        this.movie = movie

        tv_toolbar_title.text = movie.name
        tv_description.text = movie.overview
        tv_year.text = getString(R.string.year, movie.year)
        tv_country.text = getString(R.string.country, movie.country)
        tv_released.text = getString(R.string.released, movie.released)
        tv_runtime.text = getString(R.string.runtime, movie.runtime)
        tv_tagline.text = getString(R.string.tagline, movie.tagline)

        Glide.with(this)
                .load("http://img.omdbapi.com/?apikey=956febbc&i=${movie.movieId?.imdb!!}")
                .into(iv_movie)

        if (movie.isFavorite) {
            btn_favorite.setImageResource(R.drawable.ic_remove)
        } else {
            btn_favorite.setImageResource(R.drawable.ic_favorite)
        }
    }

    override fun onMovieDetailsFailed() {
        toast(getString(R.string.loading_failed))
    }

    override fun onMoviesAdded() {
        toast(getString(R.string.film_added))
    }

    override fun onMoviesAddingFailed() {
        toast(getString(R.string.adding_failed))
    }

    override fun onMovieRemoved() {
        toast(getString(R.string.movie_removed))
    }

    override fun onMovieRemoveFailed() {
        toast(getString(R.string.movie_remove_failed))
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

}
