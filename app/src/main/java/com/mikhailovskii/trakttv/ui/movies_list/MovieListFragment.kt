package com.mikhailovskii.trakttv.ui.movies_list

import android.content.Context
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
import java.util.Objects

class MovieListFragment : Fragment(), MovieListContract.MoviesListView, MoviesAdapter.OnItemClickListener {

    private val mPresenter = MovieListPresenter()
    private var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mTvNoFilms: TextView? = null
    private var mAdapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mPresenter.attachView(this)

        mTvNoFilms = view.findViewById(R.id.no_films)

        val moviesRecycler = view.findViewById<RecyclerView>(R.id.movies_list)
        moviesRecycler.layoutManager = LinearLayoutManager(context)
        moviesRecycler.addItemDecoration(DividerItemDecoration(Objects.requireNonNull<FragmentActivity>(activity), DividerItemDecoration.VERTICAL))

        mSwipeRefresh = view.findViewById(R.id.swipe_refresh)
        mSwipeRefresh!!.setOnRefreshListener {
            mPresenter.loadMovieList()
            mSwipeRefresh!!.isRefreshing = false
        }

        mAdapter = MoviesAdapter(this)
        moviesRecycler.adapter = mAdapter

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
        Toast.makeText(context, Objects.requireNonNull<Context>(context).getString(R.string.loading_failed), Toast.LENGTH_SHORT).show()
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

    companion object {

        const val FRAGMENT_NAME = "List"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_SLUG = "EXTRA_SLUG"
    }

}