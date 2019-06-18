package com.mikhailovskii.trakttv.ui.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikhailovskii.trakttv.R
import com.mikhailovskii.trakttv.data.entity.User
import com.mikhailovskii.trakttv.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ProfileFragment : Fragment(), ProfileContract.ProfileView {

    private val mPresenter = ProfilePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mPresenter.attachView(this)

        //Handle logout button
        log_out.setOnClickListener { v -> mPresenter.logOut() }

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
                .into(avatar)

        tv_email.text = user.email
        tv_id.text = user.id
        tv_login.text = user.username
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

}
