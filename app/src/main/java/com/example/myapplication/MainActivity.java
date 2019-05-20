package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements LoginContract.LoginView{

    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private Button mFacebookButton;
    private Button mLoginButton;

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
    public void onLoggedIn() {

    }

    @Override
    public void onLoginFailed() {

    }

    private void init(){
        mLoginEdit = findViewById(R.id.login_edit);
        mPasswordEdit = findViewById(R.id.password_edit);
        mFacebookButton = findViewById(R.id.facebook_button);
        mLoginButton = findViewById(R.id.login);

        mLoginPresenter = new LoginPresenter();

        mLoginButton.setOnClickListener(v -> onLoginClick());
    }

    private void onLoginClick(){
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN, mLoginEdit.getText().toString());
        bundle.putString(PASSWORD, mPasswordEdit.getText().toString());
        mLoginPresenter.emailLogin(bundle);
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);

    }

}
