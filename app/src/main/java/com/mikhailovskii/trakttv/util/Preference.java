package com.mikhailovskii.trakttv.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mikhailovskii.trakttv.data.model.User;

public class Preference {

    private static final String PREF_USER = "PREF_USER";

    private static Preference sInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private Preference(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public static synchronized Preference getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Preference(context);
        }

        return sInstance;
    }

    public void setUser(@Nullable User user) {
        mEditor.putString(PREF_USER, new Gson().toJson(user));
        mEditor.commit();
    }

    @Nullable
    public String getUser() {
        return new Gson().toJson(mSharedPreferences.getString(PREF_USER, null));
    }

}
