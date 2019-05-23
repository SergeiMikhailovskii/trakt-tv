package com.mikhailovskii.trakttv.ui.base;


import android.support.annotation.NonNull;

public interface MvpPresenter<View> {

    void attachView(@NonNull View view);

    void detachView();

}
