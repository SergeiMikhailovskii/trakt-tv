package com.mikhailovskii.trakttv.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import java.util.*


class LoginActivity : AppCompatActivity(), LoginContract.LoginView {

    private var mCallbackManager: CallbackManager? = null
    private val mPresenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter.attachView(this)

        // Handle login button
        login.setOnClickListener {
            login_layout.error = null
            password_layout.error = null
            val login = Objects.requireNonNull<Editable>(tv_login.text).toString()
            val password = Objects.requireNonNull<Editable>(et_password.text).toString()

            when {
                login == "" -> login_layout.error = getString(R.string.enter_login)
                password == "" -> password_layout.error = getString(R.string.enter_password)
                else -> {
                    val bundle = Bundle()

                    bundle.putString(LoginPresenter.EXTRA_LOGIN, tv_login.text!!.toString())
                    bundle.putString(LoginPresenter.EXTRA_PASSWORD, et_password.text!!.toString())

                    mPresenter.saveUserData(bundle)
                }
            }

        }

        // Facebook logic
        mCallbackManager = CallbackManager.Factory.create()
        facebook_login.setPermissions(FB_EMAIL_PERMISSION)
        facebook_login.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                mPresenter.proceedWithFbLogin(loginResult)
            }

            override fun onCancel() {
                toast(getString(R.string.login_fb_cancelled))
            }

            override fun onError(error: FacebookException) {
                toast(getString(R.string.failed_fb_login))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onLoggedIn() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFailed() {
        toast(getString(R.string.login_failed))
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    companion object {

        private const val FB_EMAIL_PERMISSION = "email"
    }

}
