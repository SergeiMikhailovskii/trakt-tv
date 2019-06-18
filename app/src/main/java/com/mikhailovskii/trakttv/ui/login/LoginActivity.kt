package com.mikhailovskii.trakttv.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.ui.main.MainActivity

import java.util.Objects


class LoginActivity : AppCompatActivity(), LoginContract.LoginView {

    private var mLoginLayout: TextInputLayout? = null
    private var mPasswordLayout: TextInputLayout? = null
    private var mEtLogin: TextInputEditText? = null
    private var mEtPassword: TextInputEditText? = null
    private var mBtnFacebook: LoginButton? = null
    private var mBtnLogin: Button? = null

    private var mCallbackManager: CallbackManager? = null
    private val mPresenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter.attachView(this)

        // Find views
        mLoginLayout = findViewById(R.id.login_layout)
        mPasswordLayout = findViewById(R.id.password_layout)
        mEtLogin = mLoginLayout!!.findViewById(R.id.tv_login)
        mEtPassword = mPasswordLayout!!.findViewById(R.id.password_text)
        mBtnFacebook = findViewById(R.id.facebook_login)
        mBtnLogin = findViewById(R.id.login)

        // Handle login button
        mBtnLogin!!.setOnClickListener { v ->
            mLoginLayout!!.error = null
            mPasswordLayout!!.error = null
            val login = Objects.requireNonNull<Editable>(mEtLogin!!.text).toString()
            val password = Objects.requireNonNull<Editable>(mEtPassword!!.text).toString()

            when {
                login == "" -> mLoginLayout!!.error = getString(R.string.enter_login)
                password == "" -> mPasswordLayout!!.error = getString(R.string.enter_password)
                else -> {
                    val bundle = Bundle()

                    bundle.putString(LoginPresenter.EXTRA_LOGIN, mEtLogin!!.text!!.toString())
                    bundle.putString(LoginPresenter.EXTRA_PASSWORD, mEtPassword!!.text!!.toString())

                    mPresenter.saveUserData(bundle)
                }
            }

        }

        // Facebook logic
        mCallbackManager = CallbackManager.Factory.create()
        mBtnFacebook!!.setPermissions(FB_EMAIL_PERMISSION)
        mBtnFacebook!!.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                mPresenter.proceedWithFbLogin(loginResult)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.login_fb_cancelled), Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.failed_fb_login), Toast.LENGTH_SHORT).show()
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
        Toast.makeText(applicationContext, applicationContext.getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    companion object {

        private const val FB_EMAIL_PERMISSION = "email"
    }

}
