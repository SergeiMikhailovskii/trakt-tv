package com.mikhailovskii.trakttv.ui.base;

public class BasePresenter<View> implements MvpPresenter<View> {

    public View view = null;

    @Override
    public void attachView(View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
