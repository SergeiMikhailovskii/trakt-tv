package com.mikhailovskii.trakttv.ui.login

import android.os.Bundle

import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.util.Preference
import org.json.JSONException
import org.json.JSONObject

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {

    override fun saveUserData(bundle: Bundle) {

        val login = bundle.getString(EXTRA_LOGIN)
        val password = bundle.getString(EXTRA_PASSWORD)
        val email = bundle.getString(EXTRA_EMAIL)
        val id = bundle.getString(EXTRA_ID)
        val token = bundle.getString(EXTRA_TOKEN)
        val avatar = bundle.getString(EXTRA_AVATAR)

        if (password != null && login != null) {
            val user = User(email, password, token, login, id, avatar)
            Preference.getInstance(TraktTvApp.appContext).user = user
        }

        mView!!.onLoggedIn()
    }

    override fun proceedWithFbLogin(loginResult: LoginResult) {
        val request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken()
        ) { _, response ->
            try {

                if (response.jsonObject != null) {
                    val data = response.jsonObject
                    var email: String? = null
                    var id: String? = null

                    if (data.has(FB_EMAIL_PERMISSION)) {
                        email = response.jsonObject.getString(FB_EMAIL_PERMISSION)
                    }

                    if (data.has(FB_ID_PERMISSION)) {
                        id = response.jsonObject.getString(FB_ID_PERMISSION)
                    }

                    val bundle = Bundle()
                    bundle.putString(EXTRA_TOKEN, loginResult.accessToken.token)
                    bundle.putString(EXTRA_EMAIL, email)
                    bundle.putString(EXTRA_ID, id)
                    bundle.putString(EXTRA_AVATAR, "https://graph.facebook.com/v2.2/$id/picture?height=120&type=normal")
                    bundle.putString(EXTRA_LOGIN, Profile.getCurrentProfile().name)


                    saveUserData(bundle)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            response.rawResponse
        }

        val parameters = Bundle()
        parameters.putString("fields", FB_EMAIL_PERMISSION)
        request.parameters = parameters
        request.executeAsync()
    }

    companion object {

        const val EXTRA_LOGIN = "EXTRA_LOGIN"
        const val EXTRA_PASSWORD = "EXTRA_PASSWORD"
        const val EXTRA_TOKEN = "EXTRA_TOKEN"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_AVATAR = "EXTRA_AVATAR"

        private const val FB_EMAIL_PERMISSION = "email"
        private const val FB_ID_PERMISSION = "id"
    }

}
