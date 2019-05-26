package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mikhailovskii.trakttv.TracktvApp;
import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.util.Preference;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    @Override
    public void saveUserData(@NonNull Bundle bundle) {

        String login = bundle.getString(LoginActivity.EXTRA_LOGIN);
        String password = bundle.getString(LoginActivity.EXTRA_PASSWORD);
        String email = bundle.getString(LoginActivity.EXTRA_EMAIL);
        String id = bundle.getString(LoginActivity.EXTRA_ID);
        String token = bundle.getString(LoginActivity.EXTRA_TOKEN);

        User user = new User(email, password, token, login, id);

        Preference.getInstance(TracktvApp.getAppContext()).setUser(user);

        view.onLoggedIn();
    }

}
