package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;

public interface LoginContract {

    interface LoginView{

        void onLoggedIn();

        void onLoginFailed();

    }

    interface LoginPresenter{

        void attachView(LoginActivity loginActivity);

        void detachView();

        void emailLogin(Bundle bundle);

        void facebookLogin();

    }

}
