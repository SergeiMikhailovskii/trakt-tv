package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;

import com.mikhailovskii.trakttv.TracktvApp;
import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.util.Preference;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    @Override
    public void saveUserData(Bundle bundle) {
        String login = bundle.getString(LoginActivity.EXTRA_LOGIN);
        String password = bundle.getString(LoginActivity.EXTRA_PASSWORD);
        User user = new User(null, password, null, login);

        Preference.getInstance(TracktvApp.getAppContext()).setUser(user);

        view.onLoggedIn();
    }

}
