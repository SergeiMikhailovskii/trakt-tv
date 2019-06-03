package com.mikhailovskii.trakttv.ui.profile;

import com.mikhailovskii.trakttv.data.entity.User;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface ProfileContract {

    interface ProfileView extends MvpView {

        void onLogOutSuccess();

        void onUserDataLoaded(User user);

    }

    interface ProfilePresenter extends MvpPresenter<ProfileView> {

        void getUser();

        void logOut();

    }

}
