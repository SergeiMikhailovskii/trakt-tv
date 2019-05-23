package com.mikhailovskii.trakttv;

import android.app.Application;
import android.content.Context;

public class TracktvApp extends Application {

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }

}
