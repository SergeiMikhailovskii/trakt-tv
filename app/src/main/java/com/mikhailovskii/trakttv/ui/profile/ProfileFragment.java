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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.data.entity.User;
import com.mikhailovskii.trakttv.ui.login.LoginActivity;

import java.util.Objects;


public class ProfileFragment extends Fragment
        implements ProfileContract.ProfileView {

    private ProfilePresenter mProfilePresenter = new ProfilePresenter();

    private Button mLogOutButton;
    private ImageView mAvatar;
    private TextView mLoginTextView;
    private TextView mIdTextView;
    private TextView mEmailTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfilePresenter.attachView(this);

        //Find views
        mLogOutButton = view.findViewById(R.id.log_out_button);
        mAvatar = view.findViewById(R.id.avatar);
        mLoginTextView = view.findViewById(R.id.login_textview);
        mIdTextView = view.findViewById(R.id.id_textview);
        mEmailTextView = view.findViewById(R.id.email_textview);

        //Handle logout button
        mLogOutButton.setOnClickListener(v -> mProfilePresenter.logOut());

        mProfilePresenter.getUser();
        return view;
    }

    @Override
    public void onLogOutSuccess() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUserDataLoaded(User user) {
        Glide.with(Objects.requireNonNull(getContext()))
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_error_profile)
                .apply(RequestOptions.circleCropTransform())
                .into(mAvatar);

        mEmailTextView.setText(user.getEmail());
        mIdTextView.setText(user.getId());
        mLoginTextView.setText(user.getUsername());
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}
