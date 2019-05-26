package com.mikhailovskii.trakttv.ui.profile;


import android.content.Intent;
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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
        implements ProfileContract.ProfileView {

    private ProfilePresenter mProfilePresenter = new ProfilePresenter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Find views
        Button logOutButton = view.findViewById(R.id.log_out_button);
        ImageView avatar = view.findViewById(R.id.avatar);

        //Handle logout button
        logOutButton.setOnClickListener(v -> mProfilePresenter.logOut());

        String token = mProfilePresenter.getData().getToken();

        User user = mProfilePresenter.getData();
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + user.getId() + "/picture",
                response -> {
                    try {
                        String url = response.getJSONObject().getString("url");
//                        Glide.with(Objects.requireNonNull(getContext())).load(url).into(avatar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

        request.executeAsync();

        return view;
    }

    @Override
    public void onLogOut() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
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
