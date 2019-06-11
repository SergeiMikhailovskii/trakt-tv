package com.mikhailovskii.trakttv.ui.base;


import androidx.annotation.NonNull;

public interface MvpPresenter<View> {

    void attachView(@NonNull View view);

    void detachView();

}
