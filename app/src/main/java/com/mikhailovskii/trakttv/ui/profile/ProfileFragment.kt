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
import com.mikhailovskii.trakttv.util.errorToast
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.scope.currentScope
import java.util.*

class ProfileFragment : Fragment(), ProfileContract.ProfileView {

    private val presenter by currentScope.inject<ProfileContract.ProfilePresenter>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        btn_log_out.setOnClickListener {
            presenter.logOut()
        }

        presenter.getUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
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
                .into(iv_avatar)

        if (user.email != null) {
            iv_email.visibility = View.VISIBLE
            tv_email.text = user.email
        }

        if (user.id != null) {
            iv_id.visibility = View.VISIBLE
            tv_id.text = user.id
        }
        tv_login.text = user.username
    }

    override fun onUserDataLoadedFailed() {
        errorToast(getString(R.string.loading_failed))
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

}
