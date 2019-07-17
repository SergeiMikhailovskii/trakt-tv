package com.mikhailovskii.trakttv.ui.login

import android.os.Bundle
import androidx.core.os.bundleOf
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mikhailovskii.trakttv.TraktTvApp
import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.base.BasePresenter
import com.mikhailovskii.trakttv.util.Preference
import org.json.JSONException

class LoginPresenter : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {
    override fun checkUserLoggedIn(): Boolean {
        return Preference.getInstance(TraktTvApp.appContext).user != null
    }

    override fun saveUserData(bundle: Bundle) {

        val login = bundle.getString(EXTRA_LOGIN)
        val password = bundle.getString(EXTRA_PASSWORD)
        val email = bundle.getString(EXTRA_EMAIL)
        val id = bundle.getString(EXTRA_ID)
        val token = bundle.getString(EXTRA_TOKEN)
        val avatar = bundle.getString(EXTRA_AVATAR)

        if (login != null) {
            val user = User(email, password, token, login, id, avatar)
            Preference.getInstance(TraktTvApp.appContext).user = user

            val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
            databaseReference.child("users").child(login).setValue(user)
        }

        view?.onLoggedIn()
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

                    val bundle = bundleOf(
                            EXTRA_TOKEN to loginResult.accessToken.token,
                            EXTRA_EMAIL to email,
                            EXTRA_ID to id,
                            EXTRA_AVATAR to "https://graph.facebook.com/v2.2/$id/picture?height=120&type=normal",
                            EXTRA_LOGIN to Profile.getCurrentProfile().name
                    )

                    saveUserData(bundle)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            response.rawResponse
        }

        val parameters = bundleOf("fields" to FB_EMAIL_PERMISSION)
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
