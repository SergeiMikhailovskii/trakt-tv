package com.mikhailovskii.trakttv.ui.base;

import androidx.annotation.NonNull;

public interface MvpView {

    void showEmptyState(@NonNull Boolean value);

    void showLoadingIndicator(@NonNull Boolean value);

}
