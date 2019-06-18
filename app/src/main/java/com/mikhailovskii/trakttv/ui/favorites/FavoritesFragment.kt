package com.mikhailovskii.trakttv.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import com.mikhailovskii.trakttv.util.toast
import kotlinx.android.synthetic.main.fragment_favorites.*
import java.util.*

class FavoritesFragment : Fragment(), FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    private val mPresenter = FavoritesPresenter()
    private var mAdapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        mPresenter.attachView(this)

        swipe_refresh.setOnRefreshListener { mPresenter.loadFavoriteMovies() }

        movies_list.layoutManager = LinearLayoutManager(context)
        movies_list.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))
        mAdapter = MoviesAdapter(this)
        movies_list.adapter = mAdapter

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
        mAdapter?.setData(list)
    }

    override fun onMoviesFailed() {
        // todo toas() extantion
        toast(getString(R.string.loading_failed))
    }

    override fun onMovieRemoved() {
        toast(getString(R.string.movie_deleted))
        mPresenter.loadFavoriteMovies()
    }

    override fun onMovieRemoveFailed() {
        toast(getString(R.string.movie_remove_failed))

    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            no_films.visibility = View.VISIBLE
        } else {
            no_films.visibility = View.GONE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        swipe_refresh.isRefreshing = value
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieListFragment.EXTRA_IMAGE, item.iconUrl)
        intent.putExtra(MovieListFragment.EXTRA_SLUG, item.slugId)
        intent.putExtra(MovieDetailActivity.PREV_ACTIVITY, FRAGMENT_NAME)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {
        val bundle = bundleOf(
                MovieDetailActivity.EXTRA_NAME to item.name,
                MovieDetailActivity.EXTRA_WATCHERS to item.watchers,
                MovieListFragment.EXTRA_SLUG to item.slugId,
                MovieListFragment.EXTRA_IMAGE to item.iconUrl
        )
        mPresenter.deleteMovie(bundle)
    }

    companion object {

        const val FRAGMENT_NAME = "Favorites"

    }

}

