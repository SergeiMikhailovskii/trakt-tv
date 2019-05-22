package com.mikhailovskii.trakttv.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.mikhailovskii.trakttv.data.model.User;

import java.util.Objects;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginActivity loginActivity;
    private User user;
    private final String PREFERENCES = "Preferences";

    LoginPresenter(){
        user = new User();
    }

    @Override
    public void attachView(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void detachView() {
        this.loginActivity = null;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void emailLogin(Bundle bundle) {
        String login = bundle.getString(LoginActivity.LOGIN);
        String password = bundle.getString(LoginActivity.PASSWORD);
        user.setUsername(login);
        user.setPassword(password);
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(LoginActivity.LOGIN, login).apply();
        sharedPreferences.edit().putString(LoginActivity.PASSWORD, password).apply();
        loginActivity.onLoggedIn();
    }

    @Override
    public void facebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        String token = Objects.requireNonNull(accessToken).getToken();
        user.setToken(token);
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String TOKEN = "Token";
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

}
