package com.mikhailovskii.trakttv.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.ui.main.MainActivity
import com.mikhailovskii.trakttv.util.errorToast
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import javax.inject.Inject


class LoginActivity : DaggerAppCompatActivity(), LoginContract.LoginView, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var presenter: LoginContract.LoginPresenter

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AndroidInjection.inject(this)
        presenter.attachView(this)

        if (presenter.checkUserLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Handle login button
            btn_login.setOnClickListener {
                login_layout.error = null
                password_layout.error = null
                val login = Objects.requireNonNull<Editable>(et_login.text).toString()
                val password = Objects.requireNonNull<Editable>(et_password.text).toString()

                when {
                    login == "" -> login_layout.error = getString(R.string.enter_login)
                    password == "" -> password_layout.error = getString(R.string.enter_password)
                    else -> {
                        val bundle = Bundle()

                        bundle.putString(LoginPresenter.EXTRA_LOGIN, et_login.text!!.toString())
                        bundle.putString(LoginPresenter.EXTRA_PASSWORD, et_password.text!!.toString())

                        presenter.saveUserData(bundle)
                    }
                }
            }

            // Facebook logic
            callbackManager = CallbackManager.Factory.create()
            btn_facebook_login.setPermissions(FB_EMAIL_PERMISSION)
            btn_facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    presenter.proceedWithFbLogin(loginResult)
                }

                override fun onCancel() {
                    errorToast(getString(R.string.login_fb_cancelled))
                }

                override fun onError(error: FacebookException) {
                    errorToast(getString(R.string.failed_fb_login))
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingInjector

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onLoggedIn() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFailed() {
        errorToast(getString(R.string.login_failed))
    }

    override fun showEmptyState(value: Boolean) {
        //Not implemented here
    }

    override fun showLoadingIndicator(value: Boolean) {
        //Not implemented here
    }

    companion object {

        private const val FB_EMAIL_PERMISSION = "email"

    }

}
