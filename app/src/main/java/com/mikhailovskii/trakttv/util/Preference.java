package com.mikhailovskii.trakttv.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.mikhailovskii.trakttv.data.entity.User;

public class Preference {

    private static final String PREF_USER = "PREF_USER";

    private static Preference sInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
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
        mEditor.putString(PREF_USER, new Gson().toJson(user)).commit();
    }

    @NonNull
    public User getUser() {
        return new Gson().fromJson(mSharedPreferences.getString(PREF_USER, null), User.class);
    }

}
