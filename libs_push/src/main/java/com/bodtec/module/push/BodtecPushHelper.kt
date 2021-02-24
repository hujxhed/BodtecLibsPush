package com.bodtec.module.push

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.alibaba.sdk.android.push.register.MiPushRegister
import com.bodtec.module.push.ext.str

/**
1、设置小米appId和appKey

2、在manifests中配置appkey和appsecret
<meta-data android:name="com.alibaba.app.appkey" android:value="***" />
<meta-data android:name="com.alibaba.app.appsecret" android:value="***" />

3、在AliPushPopupAct中设置要启动的App的Splash页面,在act_ali_push_popup中设置App的Logo图片

4、在App的MainAct中监听EventBus
@Subscribe(threadMode = ThreadMode.MAIN)
fun onMessageEvent(event: AliPushMessageEvent)
fun onMessageEvent(event: AliPushNotifyEvent)

 */
object BodtecPushHelper {

    //小米推送appid和appkey
    //private const val mMiAppid = "2882303761517999750"
    //private const val mMiAppKey = "5521799935750"

    /**
     * 初始化云推送通道
     */
    fun initAliPush(
        application: Application,
        miAppId: String = "",
        miAppKey: String = ""
    ) {
        createNotificationChannel(
            application.applicationContext
        )
        PushServiceFactory.init(application.applicationContext)
        val pushService = PushServiceFactory.getCloudPushService()
        pushService.register(application.applicationContext, object : CommonCallback {
            override fun onSuccess(response: String) {
                Log.d(
                    "Hujx",
                    "初始化推送成功,deviceId=${PushServiceFactory.getCloudPushService().deviceId}"
                )
                MiPushRegister.register(application.applicationContext, miAppId, miAppKey)
                HuaWeiRegister.register(application)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                Log.d(
                    "Hujx",
                    "初始化推送失败,errorcode:$errorCode,errorMessage:$errorMessage"
                )
            }
        })
    }

    fun getPushDeviceId(): String {
        return PushServiceFactory.getCloudPushService().deviceId.str()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 通知渠道的id
            val id = "1"
            // 用户可以看到的通知渠道的名字.
            val name: CharSequence = "ZK_HOME"
            // 用户可以看到的通知渠道的描述
            val description = "ZK_HOME"
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            // 配置通知渠道的属性
            mChannel.description = description
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

}