package com.mikhailovskii.trakttv.util

import com.mikhailovskii.trakttv.TraktTvApp
import es.dmoral.toasty.Toasty


fun errorToast(msg: String) {
    Toasty.error(TraktTvApp.appContext, msg, Toasty.LENGTH_SHORT).show()
}

fun successToast(msg: String) {
    Toasty.success(TraktTvApp.appContext, msg, Toasty.LENGTH_SHORT).show()
}