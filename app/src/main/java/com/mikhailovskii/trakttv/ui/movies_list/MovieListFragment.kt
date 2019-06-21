package com.mikhailovskii.trakttv.ui.movies_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.Movie
import com.mikhailovskii.trakttv.ui.adapter.MoviesAdapter
import com.mikhailovskii.trakttv.ui.movie_detail.MovieDetailActivity
import com.mikhailovskii.trakttv.util.Constants
import com.mikhailovskii.trakttv.util.toast
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class MovieListFragment : Fragment(), MovieListContract.MoviesListView, MoviesAdapter.OnItemClickListener {

    private val presenter = MovieListPresenter()
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        movies_list.layoutManager = LinearLayoutManager(context)
        movies_list.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))
        adapter = MoviesAdapter(this)
        movies_list.adapter = adapter

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = false
            presenter.loadMovieList()
        }

        presenter.loadMovieList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, Constants.IMG_URL)
        intent.putExtra(EXTRA_SLUG, item.movieId?.slug)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {

    }

    override fun onMovieListLoaded(movieList: List<Movie>) {
        adapter?.setData(movieList)
    }

    override fun onMovieListFailed() {
        toast(getString(R.string.loading_failed))
    }

    override fun showEmptyState(value: Boolean) {
        no_films.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun showLoadingIndicator(value: Boolean) {
        swipe_refresh.isRefreshing = value
    }

    companion object {
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_SLUG = "EXTRA_SLUG"
    }

}