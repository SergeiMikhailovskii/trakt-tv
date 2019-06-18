package com.mikhailovskii.trakttv.ui.login

import android.os.Bundle

import com.facebook.login.LoginResult
import com.mikhailovskii.trakttv.ui.base.MvpPresenter
import com.mikhailovskii.trakttv.ui.base.MvpView

interface LoginContract {

    interface LoginView : MvpView {

        fun onLoggedIn()

        fun onLoginFailed()

    }

    interface LoginPresenter : MvpPresenter<LoginView> {

        fun saveUserData(bundle: Bundle)

        fun proceedWithFbLogin(loginResult: LoginResult)

    }

}
