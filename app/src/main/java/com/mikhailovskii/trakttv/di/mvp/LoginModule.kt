package com.mikhailovskii.trakttv.di.mvp

import com.mikhailovskii.trakttv.di.scope.ActivityScoped
import com.mikhailovskii.trakttv.ui.login.LoginContract
import com.mikhailovskii.trakttv.ui.login.LoginPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {

    @ActivityScoped
    @Binds
    abstract fun loginPresenter(loginPresenter: LoginPresenter): LoginContract.LoginPresenter

}