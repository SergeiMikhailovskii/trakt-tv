package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;

import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    // private final String PREFERENCES = "Preferences";

    @Override
    public void saveUserData(Bundle bundle) {
        String login = bundle.getString(LoginActivity.EXTRA_LOGIN);
        String password = bundle.getString(LoginActivity.EXTRA_PASSWORD);
        User user = new User(null, password, null, login);

        // todo insert context
//        SharedPreferences sharedPreferences = view.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString(LoginActivity.EXTRA_LOGIN, login).apply();
//        sharedPreferences.edit().putString(LoginActivity.EXTRA_PASSWORD, password).apply();

        view.onLoggedIn();
    }


}
