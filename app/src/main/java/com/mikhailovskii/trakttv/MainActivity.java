package com.mikhailovskii.trakttv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements LoginContract.LoginView{

    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private LoginButton mFacebookButton;
    private CallbackManager callbackManager ;

    private static final String EMAIL = "email";

    private LoginPresenter mLoginPresenter;

    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoggedIn() {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
    }

    private void init(){
        callbackManager = CallbackManager.Factory.create();
        mLoginEdit = findViewById(R.id.login_edit);
        mPasswordEdit = findViewById(R.id.password_edit);
        mFacebookButton = findViewById(R.id.facebook_button);
        Button mLoginButton = findViewById(R.id.login);

        facebookButtonInit();

        mLoginPresenter = new LoginPresenter();

        mLoginButton.setOnClickListener(v -> onLoginClick());

    }

    private void onLoginClick(){
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN, mLoginEdit.getText().toString());
        bundle.putString(PASSWORD, mPasswordEdit.getText().toString());
        mLoginPresenter.emailLogin(bundle);
    }

    private void facebookButtonInit(){
        mFacebookButton.setReadPermissions(Collections.singletonList(EMAIL));
        mFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginPresenter.facebookLogin();
                onLoggedIn();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Request Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}