package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.di.scope.FragmentScoped
import com.mikhailovskii.trakttv.ui.profile.ProfileContract
import com.mikhailovskii.trakttv.ui.profile.ProfileFragment
import com.mikhailovskii.trakttv.ui.profile.ProfilePresenter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment

    @ActivityScoped
    @Binds
    abstract fun profilePresenter(profilePresenter: ProfilePresenter): ProfileContract.ProfilePresenter

}