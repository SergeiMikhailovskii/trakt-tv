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
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import java.util.*

class FavoritesFragment : Fragment(), FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    private val presenter = FavoritesPresenter()
    private var adapter: MoviesAdapter? = null
    private var root: View? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_favorites, container, false)
        presenter.attachView(this)

        root!!.swipe_refresh.setOnRefreshListener {
            presenter.loadFavoriteMovies()
        }

        root!!.movies_list.layoutManager = LinearLayoutManager(context)
        root!!.movies_list.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))
        adapter = MoviesAdapter(this)
        root!!.movies_list.adapter = adapter

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.loadFavoriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onMoviesLoaded(list: List<Movie>) {
        adapter?.setData(list)
    }

    override fun onMoviesFailed() {
        toast(getString(R.string.loading_failed))
    }

    override fun onMovieRemoved() {
        toast(getString(R.string.movie_deleted))
        presenter.loadFavoriteMovies()
    }

    override fun onMovieRemoveFailed() {
        toast(getString(R.string.movie_remove_failed))

    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            root!!.no_films.visibility = View.VISIBLE
        } else {
            root!!.no_films.visibility = View.GONE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        root!!.swipe_refresh.isRefreshing = value
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieListFragment.EXTRA_IMAGE, item.iconUrl)
        intent.putExtra(MovieListFragment.EXTRA_SLUG, item.movieId.slug)
        intent.putExtra(MovieDetailActivity.PREV_ACTIVITY, FRAGMENT_NAME)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {
        presenter.deleteMovie(item.name!!)
    }

    companion object {

        const val FRAGMENT_NAME = "Favorites"

    }

}

