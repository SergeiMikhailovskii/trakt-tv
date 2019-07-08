package com.mikhailovskii.trakttv.ui.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.diffutil.MovieDiffUtilCallback
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import com.mikhailovskii.trakttv.util.toast
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favorites.*
import java.util.*
import javax.inject.Inject

@ActivityScoped
class FavoritesFragment : DaggerFragment(), FavoritesContract.FavoritesView, MoviesAdapter.OnItemClickListener {

    @Inject
    lateinit var presenter: FavoritesContract.FavoritesPresenter

    private var adapter: MoviesAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        swipe_refresh.setOnRefreshListener {
            presenter.loadFavoriteMovies()
        }

        movies_list.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.rows))
        movies_list.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))
        adapter = MoviesAdapter(this)
        movies_list.adapter = adapter

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

        val movieDiffUtilCallback = MovieDiffUtilCallback(list, adapter?.movieList!!)
        val movieDiffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(movieDiffUtilCallback)
        adapter?.setData(list)
        adapter?.let {
            movieDiffResult.dispatchUpdatesTo(it)
        }
    }

    override fun onMoviesFailed() {
        toast(getString(R.string.loading_failed))
    }

    override fun onMovieRemoved() {
        toast(getString(R.string.movie_removed))
        presenter.loadFavoriteMovies()
    }

    override fun onMovieRemoveFailed() {
        toast(getString(R.string.movie_remove_failed))

    }

    override fun showEmptyState(value: Boolean) {
        if (value) {
            tv_no_films.visibility = View.VISIBLE
            movies_list.visibility = View.GONE
        } else {
            tv_no_films.visibility = View.GONE
            movies_list.visibility = View.VISIBLE
        }
    }

    override fun showLoadingIndicator(value: Boolean) {
        swipe_refresh.isRefreshing = value
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieListFragment.EXTRA_IMAGE, item.iconUrl)
        intent.putExtra(MovieListFragment.EXTRA_SLUG, item.movieId?.slug)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {
        presenter.deleteMovie(item.name!!)
    }

    companion object {

        fun newInstance(): FavoritesFragment = FavoritesFragment()

    }

}

