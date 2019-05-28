package com.mikhailovskii.trakttv;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

public class TraktTvApp extends Application {

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }

}
