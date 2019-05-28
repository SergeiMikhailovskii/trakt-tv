package com.mikhailovskii.trakttv.ui.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhailovskii.trakttv.R;


public class ListFragment extends Fragment {

    private RecyclerView mMoviesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mMoviesRecycler = view.findViewById(R.id.movies_list);

        return view;
    }

}
