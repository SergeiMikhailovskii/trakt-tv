package com.mikhailovskii.trakttv.ui.profile

import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.util.Preference
import javax.inject.Inject

class ProfilePresenter : BasePresenter<ProfileContract.ProfileView>(), ProfileContract.ProfilePresenter {

    override fun getUser() {
        val user = Preference.getInstance(TraktTvApp.appContext).user

        user?.let {
            view?.onUserDataLoaded(it)
        } ?: run {
            view?.onUserDataLoadedFailed()
        }
    }

    override fun logOut() {
        Preference.getInstance(TraktTvApp.appContext).user = null
        view?.onLogOutSuccess()
    }

}