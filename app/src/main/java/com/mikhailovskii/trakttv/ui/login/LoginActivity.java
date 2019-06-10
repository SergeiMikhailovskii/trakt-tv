package com.mikhailovskii.trakttv.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.ui.main.MainActivity;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity
        implements LoginContract.LoginView {

    private static final String FB_EMAIL_PERMISSION = "email";

    private TextInputLayout mLoginLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputEditText mEtLogin;
    private TextInputEditText mEtPassword;
    private LoginButton mBtnFacebook;
    private Button mBtnLogin;

    private CallbackManager mCallbackManager;
    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.attachView(this);

        // Find views
        mLoginLayout = findViewById(R.id.login_layout);
        mPasswordLayout = findViewById(R.id.password_layout);
        mEtLogin = mLoginLayout.findViewById(R.id.tv_login);
        mEtPassword = mPasswordLayout.findViewById(R.id.password_text);
        mBtnFacebook = findViewById(R.id.facebook_login);
        mBtnLogin = findViewById(R.id.login);

        // Handle login button
        mBtnLogin.setOnClickListener(v -> {
            mLoginLayout.setError(null);
            mPasswordLayout.setError(null);
            String login = Objects.requireNonNull(mEtLogin.getText()).toString();
            String password = Objects.requireNonNull(mEtPassword.getText()).toString();

            if (login.equals("")) {
                mLoginLayout.setError("Enter login!");
            } else if (password.equals("")) {
                mPasswordLayout.setError("Enter password");
            } else {
                Bundle bundle = new Bundle();

                bundle.putString(LoginPresenter.EXTRA_LOGIN, mEtLogin.getText().toString());
                bundle.putString(LoginPresenter.EXTRA_PASSWORD, mEtPassword.getText().toString());

                mPresenter.saveUserData(bundle);
            }

        });

        // Facebook logic
        mCallbackManager = CallbackManager.Factory.create();
        mBtnFacebook.setPermissions(FB_EMAIL_PERMISSION);
        mBtnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mPresenter.proceedWithFbLogin(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.login_fb_cancelled), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.failed_fb_login), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoggedIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}
