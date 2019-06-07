package com.mikhailovskii.trakttv.ui.profile;

import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.entity.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.util.Preference;

public class ProfilePresenter extends BasePresenter<ProfileContract.ProfileView>
        implements ProfileContract.ProfilePresenter {

    @Override
    public void getUser() {
        User user = Preference.getInstance(TraktTvApp.getAppContext()).getUser();
        // todo user can be null?
        mView.onUserDataLoaded(user);
    }

    @Override
    public void logOut() {
        Preference.getInstance(TraktTvApp.getAppContext()).setUser(null);
        mView.onLogOutSuccess();
    }

}