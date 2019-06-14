package com.mikhailovskii.trakttv.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment;
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment;
import com.mikhailovskii.trakttv.ui.profile.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        FragmentTransaction startTransaction = getSupportFragmentManager().beginTransaction();
        MovieListFragment movieListFragment = new MovieListFragment();
        startTransaction.add(R.id.fragmentLayout, movieListFragment);
        startTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        startTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment = null;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.movies:
                    menuItem.setChecked(true);
                    fragment = new MovieListFragment();
                    break;
                case R.id.favorites:
                    menuItem.setChecked(true);
                    fragment = new FavoritesFragment();
                    break;
                case R.id.profile:
                    menuItem.setChecked(true);
                    fragment = new ProfileFragment();
                    break;
            }

            if (fragment != null) {
                fragmentTransaction.replace(R.id.fragmentLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }

            return false;
        });
    }

}
