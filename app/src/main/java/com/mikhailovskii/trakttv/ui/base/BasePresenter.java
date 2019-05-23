package com.mikhailovskii.trakttv.ui.base;

import android.support.annotation.NonNull;

public class BasePresenter<View> implements MvpPresenter<View> {

    public View view = null;

    @Override
    public void attachView(@NonNull View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
