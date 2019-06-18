package com.mikhailovskii.trakttv.ui.profile

import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.util.Preference

class ProfilePresenter : BasePresenter<ProfileContract.ProfileView>(), ProfileContract.ProfilePresenter {

    override fun getUser() {
        val user = Preference.getInstance(TraktTvApp.appContext).user
        mView!!.onUserDataLoaded(user)
    }

    override fun logOut() {
        Preference.getInstance(TraktTvApp.appContext).user = null
        mView!!.onLogOutSuccess()
    }

}