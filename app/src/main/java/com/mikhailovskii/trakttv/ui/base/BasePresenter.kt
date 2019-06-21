package com.mikhailovskii.trakttv.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<View> : MvpPresenter<View> {

    var mView: View? = null
    var mCompositeDisposable = CompositeDisposable()

    override fun attachView(view: View) {
        mView = view
    }

    override fun detachView() {
        mCompositeDisposable.clear()
        mView = null
    }

}
