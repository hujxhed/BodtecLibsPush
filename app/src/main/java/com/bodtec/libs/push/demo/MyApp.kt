package com.bodtec.libs.push.demo

import android.app.Application
import com.bodtec.module.push.BodtecPushHelper

/**
 * hujx 2/24/21
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        BodtecPushHelper.initAliPush(this)
    }

}