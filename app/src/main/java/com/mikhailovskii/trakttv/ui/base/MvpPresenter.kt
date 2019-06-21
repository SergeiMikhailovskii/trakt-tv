package com.mikhailovskii.trakttv.ui.base

interface MvpPresenter<View> {

    fun attachView(view: View)

    fun detachView()

}
