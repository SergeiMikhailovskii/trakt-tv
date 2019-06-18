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
import com.mikhailovskii.trakttv.util.toast
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class MovieListFragment : Fragment(), MovieListContract.MoviesListView, MoviesAdapter.OnItemClickListener {

    private val mPresenter = MovieListPresenter()
    private var mAdapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mPresenter.attachView(this)

        movies_list.layoutManager = LinearLayoutManager(context)
        movies_list.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))

        swipe_refresh.setOnRefreshListener {
            mPresenter.loadMovieList()
            swipe_refresh.isRefreshing = false
        }

        mAdapter = MoviesAdapter(this)
        movies_list.adapter = mAdapter

        mPresenter.loadMovieList()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onItemClicked(position: Int, item: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, item.iconUrl)
        intent.putExtra(EXTRA_SLUG, item.slugId)
        intent.putExtra(MovieDetailActivity.PREV_ACTIVITY, FRAGMENT_NAME)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, item: Movie) {

    }

    override fun onMovieListLoaded(movieList: List<Movie>) {
        mAdapter!!.setData(movieList)
    }

    override fun onMovieListFailed() {
        toast(getString(R.string.loading_failed))
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

    companion object {

        const val FRAGMENT_NAME = "List"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_SLUG = "EXTRA_SLUG"
    }

}