package com.mikhailovskii.trakttv.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.mikhailovskii.trakttv.TraktTvApp;
import com.mikhailovskii.trakttv.data.entity.User;
import com.mikhailovskii.trakttv.ui.base.BasePresenter;
import com.mikhailovskii.trakttv.util.Preference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
    public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
    public static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    private static final String FB_EMAIL_PERMISSION = "email";
    private static final String FB_ID_PERMISSION = "id";

    @Override
    public void saveUserData(@NonNull Bundle bundle) {

        String login = bundle.getString(EXTRA_LOGIN);
        String password = bundle.getString(EXTRA_PASSWORD);
        String email = bundle.getString(EXTRA_EMAIL);
        String id = bundle.getString(EXTRA_ID);
        String token = bundle.getString(EXTRA_TOKEN);
        String avatar = bundle.getString(EXTRA_AVATAR);

        User user = new User(email, password, token, login, id, avatar);

        Preference.getInstance(TraktTvApp.getAppContext()).setUser(user);

        mView.onLoggedIn();
    }

    @Override
    public void proceedWithFbLogin(@NotNull LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                (object, response) -> {
                    try {

                        if (response.getJSONObject() != null) {
                            JSONObject data = response.getJSONObject();
                            String email = null;
                            String id = null;

                            if (data.has(FB_EMAIL_PERMISSION)) {
                                email = response.getJSONObject().getString(FB_EMAIL_PERMISSION);
                            }

                            if (data.has(FB_ID_PERMISSION)) {
                                id = response.getJSONObject().getString(FB_ID_PERMISSION);
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString(EXTRA_TOKEN, loginResult.getAccessToken().getToken());
                            bundle.putString(EXTRA_EMAIL, email);
                            bundle.putString(EXTRA_ID, id);
                            bundle.putString(EXTRA_AVATAR, "https://graph.facebook.com/v2.2/" + id + "/picture?height=120&type=normal");
                            bundle.putString(EXTRA_LOGIN, Profile.getCurrentProfile().getName());


                            saveUserData(bundle);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    response.getRawResponse();
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", FB_EMAIL_PERMISSION);
        request.setParameters(parameters);
        request.executeAsync();
    }

}
