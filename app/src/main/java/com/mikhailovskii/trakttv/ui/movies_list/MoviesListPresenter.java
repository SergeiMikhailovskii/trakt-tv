package com.mikhailovskii.trakttv.ui.movies_list;

import android.util.Log;

import com.mikhailovskii.trakttv.data.model.Movie;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviesListPresenter extends BasePresenter<MoviesListContract.MoviesListView>
        implements MoviesListContract.MoviesListPresenter {

    @Override
    public void downloadMoviesList() {
        Log.i(MoviesListFragment.TAG, "In download movies list");
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 1", "Motto 1"));
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 2", "Motto 2"));
        list.add(new Movie("https://cdn4.iconfinder.com/data/icons/photo-video-outline/100/objects-17-512.png", "Movie 3", "Motto 3"));

/*        try {
            list = new MoviesListDownload().execute().get();
        }catch (ExecutionException|InterruptedException e){
            e.printStackTrace();
        }*/


        view.onMoviesListDownloadSuccess(list);

    }

    @Override
    public void downloadMovieInfo() {

    }

}
