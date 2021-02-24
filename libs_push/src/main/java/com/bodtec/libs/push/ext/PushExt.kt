package com.bodtec.libs.push.ext

import android.text.TextUtils

fun String?.str(defaultValue: String = ""): String {
    var result = defaultValue
    if (null != this && !TextUtils.isEmpty(this)) {
        result = this.trim { it <= ' ' }.replace("\r".toRegex(), "")
    }
    return result
}