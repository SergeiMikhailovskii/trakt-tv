package com.mikhailovskii.trakttv.ui.profile;

import com.mikhailovskii.trakttv.TracktvApp;
import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.util.Preference;

public class ProfilePresenter extends BasePresenter<ProfileContract.ProfileView>
        implements ProfileContract.ProfilePresenter {

    @Override
    public User getData() {
        return Preference.getInstance(TracktvApp.getAppContext()).getUser();
    }

    @Override
    public void logOut() {
        Preference.getInstance(TracktvApp.getAppContext()).setUser(null);
        view.onLogOutSuccess();
    }

}