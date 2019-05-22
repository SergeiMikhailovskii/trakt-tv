package com.mikhailovskii.trakttv.ui.base;


public interface MvpView {

    void showMessage(String message);

    void showEmptyState(Boolean value);

    void showLoadingIndicator(Boolean value);

}
