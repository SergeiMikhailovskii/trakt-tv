package com.mikhailovskii.trakttv.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.ui.favorites.FavoritesFragment
import com.mikhailovskii.trakttv.ui.movies_list.MovieListFragment
import com.mikhailovskii.trakttv.ui.profile.ProfileFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startTransaction = supportFragmentManager.beginTransaction()
        val movieListFragment = MovieListFragment()
        startTransaction.add(R.id.fragmentLayout, movieListFragment)
        startTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        startTransaction.commit()

        bottom_nav.setOnNavigationItemSelectedListener { menuItem ->
            var fragment: Fragment? = null
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            when (menuItem.itemId) {
                R.id.movies -> {
                    menuItem.isChecked = true
                    fragment = MovieListFragment()
                }
                R.id.favorites -> {
                    menuItem.isChecked = true
//                    fragment = FavoritesFragment()
                    fragment = FavoritesFragment.newInstance()
                }
                R.id.profile -> {
                    menuItem.isChecked = true
                    fragment = ProfileFragment()
                }
            }

            if (fragment != null) {
                fragmentTransaction.replace(R.id.fragmentLayout, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
            }

            false
        }
    }

}
