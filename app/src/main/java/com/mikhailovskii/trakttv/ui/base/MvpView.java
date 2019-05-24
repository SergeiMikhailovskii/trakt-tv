package com.mikhailovskii.trakttv.ui.base;


import android.support.annotation.NonNull;

public interface MvpView {

    void showMessage(@NonNull String message);

    void showEmptyState(@NonNull Boolean value);

    void showLoadingIndicator(@NonNull Boolean value);

}
