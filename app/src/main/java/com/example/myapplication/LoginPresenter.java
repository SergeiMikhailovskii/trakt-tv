package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private MainActivity mainActivity;

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
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(MainActivity.LOGIN, login);
        sharedPreferences.edit().putString(MainActivity.PASSWORD, password);
        sharedPreferences.edit().apply();

    }

    @Override
    public void facebookLogin() {

    }

}
