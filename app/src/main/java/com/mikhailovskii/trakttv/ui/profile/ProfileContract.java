package com.mikhailovskii.trakttv.ui.profile;

import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.base.MvpPresenter;
import com.mikhailovskii.trakttv.ui.base.MvpView;

public interface ProfileContract {

    interface ProfileView extends MvpView{

        void onLogOutSuccess();

        void onUserDataLoaded();

    }

    interface ProfilePresenter extends MvpPresenter<ProfileView>{

        User getData();

        void logOut();

    }

}
