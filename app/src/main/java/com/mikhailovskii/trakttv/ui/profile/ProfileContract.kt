package com.mikhailovskii.trakttv.ui.profile

import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.base.MvpPresenter
import com.mikhailovskii.trakttv.ui.base.MvpView

interface ProfileContract {

    interface ProfileView : MvpView {

        fun onLogOutSuccess()

        fun onUserDataLoaded(user: User)

    }

    interface ProfilePresenter : MvpPresenter<ProfileView> {

        fun getUser()

        fun logOut()

    }

}
