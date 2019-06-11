package com.mikhailovskii.trakttv.ui.base;

import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<View> implements MvpPresenter<View> {

    public View mView = null;
    public CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(@NonNull View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        mView = null;
    }

}
