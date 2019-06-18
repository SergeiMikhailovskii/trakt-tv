package com.mikhailovskii.trakttv.ui.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.login.LoginActivity

import java.util.Objects


class ProfileFragment : Fragment(), ProfileContract.ProfileView {

    private val mPresenter = ProfilePresenter()

    private var mBtnLogOut: Button? = null
    private var mIvAvatar: ImageView? = null
    private var mTvLogin: TextView? = null
    private var mTvId: TextView? = null
    private var mTvEmail: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mPresenter.attachView(this)

        //Find views
        mBtnLogOut = view.findViewById(R.id.log_out)
        mIvAvatar = view.findViewById(R.id.avatar)
        mTvLogin = view.findViewById(R.id.tv_login)
        mTvId = view.findViewById(R.id.tv_id)
        mTvEmail = view.findViewById(R.id.tv_email)

        //Handle logout button
        mBtnLogOut!!.setOnClickListener { v -> mPresenter.logOut() }

        mPresenter.getUser()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun onLogOutSuccess() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onUserDataLoaded(user: User) {
        Glide.with(Objects.requireNonNull<Context>(context))
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_error_profile)
                .into(mIvAvatar!!)

        mTvEmail!!.text = user.email
        mTvId!!.text = user.id
        mTvLogin!!.text = user.username
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

}
