package com.mikhailovskii.trakttv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;

import java.util.Objects;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private MainActivity mainActivity;
    private User user;
    private final String PREFERENCES = "Preferences";

    LoginPresenter(){
        user = new User();
    }

    @Override
    public void attachView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void detachView() {
        this.mainActivity = null;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void emailLogin(Bundle bundle) {
        String login = bundle.getString(MainActivity.LOGIN);
        String password = bundle.getString(MainActivity.PASSWORD);
        user.setUsername(login);
        user.setPassword(password);
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(MainActivity.LOGIN, login).apply();
        sharedPreferences.edit().putString(MainActivity.PASSWORD, password).apply();
        mainActivity.onLoggedIn();
    }

    @Override
    public void facebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        String token = Objects.requireNonNull(accessToken).getToken();
        user.setToken(token);
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String TOKEN = "Token";
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

}
