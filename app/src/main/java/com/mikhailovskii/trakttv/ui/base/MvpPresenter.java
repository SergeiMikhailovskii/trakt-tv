package com.mikhailovskii.trakttv.ui.base;


public interface MvpPresenter<View> {

    void attachView(View view);

    void detachView();

}
