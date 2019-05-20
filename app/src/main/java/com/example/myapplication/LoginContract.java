package com.example.myapplication;

import android.os.Bundle;

public interface LoginContract {

    interface LoginView{

        void onLoggedIn();
        void onLoginFailed();

    }

    interface LoginPresenter{

        void attachView(MainActivity mainActivity);
        void detachView();
        void emailLogin(Bundle bundle);
        void facebookLogin();

    }

}
