package com.mikhailovskii.trakttv.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<View> : MvpPresenter<View> {

    var view: View? = null
    var compositeDisposable = CompositeDisposable()

    override fun attachView(view: View) {
        this.view = view
    }

    override fun detachView() {
        compositeDisposable.clear()
        view = null
    }

}
