package com.mikhailovskii.trakttv.ui.movie_detail

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.MovieDetailView {

    private val mPresenter = MovieDetailPresenter()

    private var mTvDescription: TextView? = null
    private var mTvCountry: TextView? = null
    private var mTvRuntime: TextView? = null
    private var mTvReleased: TextView? = null
    private var mTvTagline: TextView? = null
    private var mTvYear: TextView? = null
    private var mTvToolbarTitle: TextView? = null
    private var mTvNoInfo: TextView? = null
    private var mIvMovie: ImageView? = null
    private var mBtnBack: ImageButton? = null
    private var mFabFavorite: FloatingActionButton? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mToolbar: Toolbar? = null

    private var mSlugId: String? = null
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        mPresenter.attachView(this)

        setToolbar()

        mSlugId = intent.getStringExtra(MovieListFragment.EXTRA_SLUG)
        mUrl = intent.getStringExtra(MovieListFragment.EXTRA_IMAGE)

        mTvDescription = findViewById(R.id.tv_description)
        mTvCountry = findViewById(R.id.tv_country)
        mTvRuntime = findViewById(R.id.tv_runtime)
        mTvReleased = findViewById(R.id.tv_released)
        mTvTagline = findViewById(R.id.tv_tagline)
        mTvYear = findViewById(R.id.tv_year)
        mTvNoInfo = findViewById(R.id.tv_no_info)
        mIvMovie = findViewById(R.id.movie_image)
        mFabFavorite = findViewById(R.id.favorite)

        if (intent.getStringExtra(PREV_ACTIVITY) == FavoritesFragment.FRAGMENT_NAME) {
            mFabFavorite!!.hide()
        }

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh)
        mSwipeRefreshLayout!!.setOnRefreshListener { mPresenter.loadMovieDetails(mSlugId) }

        mPresenter.loadMovieDetails(mSlugId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            mTvNoInfo!!.visibility = View.VISIBLE
        } else {
            mTvNoInfo!!.visibility = View.GONE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        mSwipeRefreshLayout!!.isRefreshing = value
    }

    override fun onMovieDetailsLoaded(movie: Movie) {
        mFabFavorite!!.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString(EXTRA_NAME, movie.name)
            bundle.putInt(EXTRA_WATCHERS, movie.watchers)
            bundle.putString(MovieListFragment.EXTRA_SLUG, movie.movieId!!.slug)
            bundle.putString(MovieListFragment.EXTRA_IMAGE, mUrl)
            mPresenter.addMovieToFavorites(bundle)
        }

        mTvToolbarTitle!!.text = movie.name
        mTvDescription!!.text = movie.overview
        mTvYear!!.text = getString(R.string.year, movie.year)
        mTvCountry!!.text = getString(R.string.country, movie.country)
        mTvReleased!!.text = getString(R.string.released, movie.released)
        mTvRuntime!!.text = getString(R.string.runtime, movie.runtime)
        mTvTagline!!.text = getString(R.string.tagline, movie.tagline)

        Glide.with(this).load(mUrl).into(mIvMovie!!)
    }

    override fun onMovieDetailsFailed() {
        Toast.makeText(this, getString(R.string.adding_failed), Toast.LENGTH_SHORT).show()
    }

    override fun onMoviesAdded() {
        Toast.makeText(this, getString(R.string.film_added), Toast.LENGTH_SHORT).show()
    }

    override fun onMoviesAddingFailed() {

    }

    private fun setToolbar() {
        mToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(mToolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        mTvToolbarTitle = mToolbar!!.findViewById(R.id.toolbar_title)
        mBtnBack = mToolbar!!.findViewById(R.id.back)
        mBtnBack!!.setOnClickListener { v -> onBackPressed() }
    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_WATCHERS = "EXTRA_WATCHERS"
        const val PREV_ACTIVITY = "PREV_ACTIVITY"
    }

}
