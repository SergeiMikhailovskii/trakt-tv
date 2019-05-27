package com.mikhailovskii.trakttv.ui.profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.model.User;
import com.mikhailovskii.trakttv.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment
        implements ProfileContract.ProfileView {

    private final String USER_ID = "100037137486079";
    private ProfilePresenter mProfilePresenter = new ProfilePresenter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfilePresenter.attachView(this);

        User user = mProfilePresenter.getData();

        //Find views
        Button logOutButton = view.findViewById(R.id.log_out_button);
        ImageView avatar = view.findViewById(R.id.avatar);

        //Setting round shape
        avatar.setImageResource(R.drawable.avatar_shape);

        //Handle logout button
        logOutButton.setOnClickListener(v -> mProfilePresenter.logOut());

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/" + user.getId() + "/picture",
                response -> {
                    JSONObject jsonObject = response.getJSONObject();
                    try {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String url = data.getString("url");
                        Glide.with(getContext()).load(Uri.parse(url)).into(avatar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

        request.executeAsync();

//        final String url = "https://scontent.xx.fbcdn.net/v/t1.0-1/c15.0.50.50a/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?_nc_cat=1&_nc_ht=scontent.xx&oh=60206c93f484956b6672f23d36e5db8c&oe=5D54D1E8";
//
//        Glide.with(getContext()).load(Uri.parse(url)).into(avatar);


        return view;
    }

    @Override
    public void onLogOutSuccess() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUserDataLoaded() {

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
