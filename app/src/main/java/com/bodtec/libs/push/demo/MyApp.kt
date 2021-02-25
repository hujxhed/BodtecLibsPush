package com.bodtec.libs.push.demo

import android.app.Application
import com.bodtec.module.push.BodtecPushHelper

/**
 * hujx 2/24/21
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        BodtecPushHelper.initAliPush(
            this,
            channelName = "ZK_HOME",//通知渠道名
            miAppId = "2882303761517999750",//小米appid
            miAppKey = "5521799935750",//小米appkey
            splashFullPath = "com.bodtec.libs.push.demo.SplashAct"
        )
    }

}