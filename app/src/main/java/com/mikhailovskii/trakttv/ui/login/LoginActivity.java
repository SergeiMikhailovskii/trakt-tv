package com.mikhailovskii.trakttv.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.ui.main.MainActivity;

import java.util.Collections;

public class LoginActivity extends AppCompatActivity
        implements LoginContract.LoginView {

    private static final String FB_EMAIL_PERMISSION = "email";

    public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
    public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
    public static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";

    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private LoginButton mFacebookButton;
    private Button mLoginButton;

    private CallbackManager mCallbackManager;
    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.attachView(this);

        // Find views
        mLoginEdit = findViewById(R.id.login_edit);
        mPasswordEdit = findViewById(R.id.password_edit);
        mFacebookButton = findViewById(R.id.facebook_button);
        mLoginButton = findViewById(R.id.login);

        // Handle login button
        mLoginButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_LOGIN, mLoginEdit.getText().toString());
            bundle.putString(EXTRA_PASSWORD, mPasswordEdit.getText().toString());

            mPresenter.saveUserData(bundle);
        });

        // Facebook logic
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookButton.setReadPermissions(Collections.singletonList(FB_EMAIL_PERMISSION));
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_TOKEN, loginResult.getAccessToken().getToken());
                bundle.putString(EXTRA_EMAIL, null); // todo also get email from FACEBOOK

                mPresenter.saveUserData(bundle);
            }

            @Override
            public void onCancel() {
                // todo move UI strings into String file
                Toast.makeText(getApplicationContext(), "Login with FB cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                // todo move UI strings into String file
                Toast.makeText(getApplicationContext(), "Failed to login with FB", Toast.LENGTH_SHORT).show();
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
        // todo move UI strings into String file
        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}
