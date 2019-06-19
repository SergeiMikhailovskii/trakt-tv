package com.mikhailovskii.trakttv.ui.movie_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.toast

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.MovieDetailView {

    private val presenter = MovieDetailPresenter()

    private var slugId: String? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        presenter.attachView(this)

        setToolbar()

        slugId = intent.getStringExtra(MovieListFragment.EXTRA_SLUG)
        url = intent.getStringExtra(MovieListFragment.EXTRA_IMAGE)

        if (intent.getStringExtra(PREV_ACTIVITY) == FavoritesFragment.FRAGMENT_NAME) {
            favorite.hide()
        }

        swipe_refresh.setOnRefreshListener {
            presenter.loadMovieDetails(slugId)
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
        favorite.setOnClickListener {
            val bundle = bundleOf(
                    EXTRA_NAME to movie.name,
                    EXTRA_WATCHERS to movie.watchers,
                    MovieListFragment.EXTRA_SLUG to slugId,
                    MovieListFragment.EXTRA_IMAGE to url
            )
            presenter.addMovieToFavorites(bundle)
        }

        toolbar_title.text = movie.name
        tv_description.text = movie.overview
        tv_year.text = getString(R.string.year, movie.year)
        tv_country.text = getString(R.string.country, movie.country)
        tv_released.text = getString(R.string.released, movie.released)
        tv_runtime.text = getString(R.string.runtime, movie.runtime)
        tv_tagline.text = getString(R.string.tagline, movie.tagline)

        Glide.with(this)
                .load(url)
                .into(movie_image)
    }

    override fun onMovieDetailsFailed() {
        toast(getString(R.string.adding_failed))
    }

    override fun onMoviesAdded() {
        toast(getString(R.string.film_added))
    }

    override fun onMoviesAddingFailed() {

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_WATCHERS = "EXTRA_WATCHERS"
        const val PREV_ACTIVITY = "PREV_ACTIVITY"
    }

}
