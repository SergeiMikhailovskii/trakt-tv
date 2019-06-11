package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.login.LoginResult;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface LoginContract {

    interface LoginView extends MvpView {

        void onLoggedIn();

        void onLoginFailed();

    }

    interface LoginPresenter extends MvpPresenter<LoginView> {

        void saveUserData(@NonNull Bundle bundle);

        void proceedWithFbLogin(@NonNull LoginResult loginResult);

    }

}
