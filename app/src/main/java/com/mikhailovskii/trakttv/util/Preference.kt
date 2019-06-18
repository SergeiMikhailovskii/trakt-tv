package com.mikhailovskii.trakttv.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.google.gson.Gson
import com.mikhailovskii.trakttv.data.entity.User

class Preference @SuppressLint("CommitPrefEdits")
private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences
    private val mEditor: SharedPreferences.Editor

    var user: User?
        get() = Gson().fromJson<User>(mSharedPreferences.getString(PREF_USER, null), User::class.java!!)
        set(user) {
            mEditor.putString(PREF_USER, Gson().toJson(user)).commit()
        }

    init {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mEditor = mSharedPreferences.edit()
    }

    companion object {

        private val PREF_USER = "PREF_USER"

        private var sInstance: Preference? = null

        @Synchronized
        fun getInstance(context: Context): Preference {
            if (sInstance == null) {
                sInstance = Preference(context)
            }

            return sInstance
        }
    }

}
