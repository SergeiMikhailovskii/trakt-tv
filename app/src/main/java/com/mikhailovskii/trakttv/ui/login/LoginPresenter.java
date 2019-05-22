package com.mikhailovskii.trakttv.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

import java.util.Objects;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    private User user;
    // private final String PREFERENCES = "Preferences";

    LoginPresenter() {
        user = new User();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void emailLogin(Bundle bundle) {
        String login = bundle.getString(LoginActivity.LOGIN);
        String password = bundle.getString(LoginActivity.PASSWORD);
        user.setUsername(login);
        user.setPassword(password);

        // todo insert context
//        SharedPreferences sharedPreferences = view.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString(LoginActivity.LOGIN, login).apply();
//        sharedPreferences.edit().putString(LoginActivity.PASSWORD, password).apply();

        view.onLoggedIn();
    }

    @Override
    public void facebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        String token = Objects.requireNonNull(accessToken).getToken();
        user.setToken(token);
//        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        String TOKEN = "Token";
//        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

}
