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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProfileFragment extends Fragment implements ProfileContract.ProfileView {

    private ProfilePresenter mPresenter = new ProfilePresenter();

    private Button mBtnLogOut;
    private ImageView mIvAvatar;
    private TextView mTvLogin;
    private TextView mTvId;
    private TextView mTvEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mPresenter.attachView(this);

        //Find views
        mBtnLogOut = view.findViewById(R.id.log_out);
        mIvAvatar = view.findViewById(R.id.avatar);
        mTvLogin = view.findViewById(R.id.tv_login);
        mTvId = view.findViewById(R.id.tv_id);
        mTvEmail = view.findViewById(R.id.tv_email);

        //Handle logout button
        mBtnLogOut.setOnClickListener(v -> mPresenter.logOut());

        mPresenter.getUser();

        return view;
    }

    // todo do not forget to detach view
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onLogOutSuccess() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUserDataLoaded(@NotNull User user) {
        Glide.with(Objects.requireNonNull(getContext()))
                .load(user.getAvatar())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_error_profile)
                .into(mIvAvatar);

        mTvEmail.setText(user.getEmail());
        mTvId.setText(user.getId());
        mTvLogin.setText(user.getUsername());
    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}
