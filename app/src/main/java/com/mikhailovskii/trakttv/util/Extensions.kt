package com.mikhailovskii.trakttv.util

import android.widget.Toast
import com.mikhailovskii.trakttv.TraktTvApp

fun toast(msg: String) {
    Toast.makeText(TraktTvApp.appContext, msg, Toast.LENGTH_SHORT).show()
}