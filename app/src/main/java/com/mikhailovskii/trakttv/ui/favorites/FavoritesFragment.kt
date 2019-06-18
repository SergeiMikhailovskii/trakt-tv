package com.mikhailovskii.trakttv.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import java.util.Objects

class FavoritesFragment : Fragment(), FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    private val mPresenter = FavoritesPresenter()
    private var mTvNoFilms: TextView? = null
    private var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mMoviesRecycler: RecyclerView? = null
    private var mAdapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        mPresenter.attachView(this)

        mTvNoFilms = view.findViewById(R.id.no_films)
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh)
        mSwipeRefresh!!.setOnRefreshListener { mPresenter.loadFavoriteMovies() }

        mMoviesRecycler = view.findViewById(R.id.movies_list)
        mMoviesRecycler!!.layoutManager = LinearLayoutManager(context)
        mMoviesRecycler!!.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))
        mAdapter = MoviesAdapter(this)
        mMoviesRecycler!!.adapter = mAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadFavoriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onMoviesLoaded(list: List<Movie>) {
        mAdapter!!.setData(list)
    }

    override fun onMoviesFailed() {
        Toast.makeText(context, getString(R.string.loading_failed), Toast.LENGTH_SHORT).show()
    }

    override fun onMovieRemoved() {
        Toast.makeText(context, getString(R.string.movie_deleted), Toast.LENGTH_SHORT).show()
        mPresenter.loadFavoriteMovies()
    }

    override fun onMovieRemoveFailed() {
        Toast.makeText(context, getString(R.string.movie_remove_failed), Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            mTvNoFilms!!.visibility = View.VISIBLE
        } else {
            mTvNoFilms!!.visibility = View.GONE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        mSwipeRefresh!!.isRefreshing = value
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieListFragment.EXTRA_IMAGE, item.iconUrl)
        intent.putExtra(MovieListFragment.EXTRA_SLUG, item.slugId)
        intent.putExtra(MovieDetailActivity.PREV_ACTIVITY, FRAGMENT_NAME)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {
        val bundle = Bundle()
        bundle.putString(MovieDetailActivity.EXTRA_NAME, item.name)
        bundle.putInt(MovieDetailActivity.EXTRA_WATCHERS, item.watchers)
        bundle.putString(MovieListFragment.EXTRA_SLUG, item.slugId)
        bundle.putString(MovieListFragment.EXTRA_IMAGE, item.iconUrl)
        mPresenter.deleteMovie(bundle)
    }

    companion object {

        const val FRAGMENT_NAME = "Favorites"
    }

}

