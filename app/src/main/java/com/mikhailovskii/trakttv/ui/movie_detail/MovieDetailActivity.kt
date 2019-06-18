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
import org.jetbrains.anko.toast

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.MovieDetailView {

    private val mPresenter = MovieDetailPresenter()

    private var mSlugId: String? = null
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        mPresenter.attachView(this)

        setToolbar()

        mSlugId = intent.getStringExtra(MovieListFragment.EXTRA_SLUG)
        mUrl = intent.getStringExtra(MovieListFragment.EXTRA_IMAGE)

        if (intent.getStringExtra(PREV_ACTIVITY) == FavoritesFragment.FRAGMENT_NAME) {
            favorite.hide()
        }

        swipe_refresh.setOnRefreshListener { mPresenter.loadMovieDetails(mSlugId) }

        mPresenter.loadMovieDetails(mSlugId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
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
            val bundle = Bundle()
            bundle.putString(EXTRA_NAME, movie.name)
            bundle.putInt(EXTRA_WATCHERS, movie.watchers)
            bundle.putString(MovieListFragment.EXTRA_SLUG, movie.movieId!!.slug)
            bundle.putString(MovieListFragment.EXTRA_IMAGE, mUrl)
            mPresenter.addMovieToFavorites(bundle)
        }

        toolbar_title.text = movie.name
        tv_description.text = movie.overview
        tv_year.text = getString(R.string.year, movie.year)
        tv_country.text = getString(R.string.country, movie.country)
        tv_released.text = getString(R.string.released, movie.released)
        tv_runtime.text = getString(R.string.runtime, movie.runtime)
        tv_tagline.text = getString(R.string.tagline, movie.tagline)

        Glide.with(this).load(mUrl).into(movie_image)
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

        back.setOnClickListener { onBackPressed() }
    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_WATCHERS = "EXTRA_WATCHERS"
        const val PREV_ACTIVITY = "PREV_ACTIVITY"
    }

}
