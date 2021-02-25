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

1、在App/manifests/application节点下设置推送的appkey、appsecret
<meta-data android:name="com.alibaba.app.appkey" android:value="25893456" />
<meta-data android:name="com.alibaba.app.appsecret" android:value="bbf0f540e8868f2ae1fbac66650eb5ed" />

2、在App/manifests/application节点下设置华为appid
<meta-data android:name="com.huawei.hms.client.appid"  android:value="100726517" />

3、设置小米appId和appKey(代码设置)

4、在AliPushPopupAct中设置要启动的App的Splash页面

5、在App的MainAct中监听EventBus
@Subscribe(threadMode = ThreadMode.MAIN)
fun onMessageEvent(event: AliPushMessageEvent)
fun onMessageEvent(event: AliPushNotifyEvent)

 */
object BodtecPushHelper {

    /**
     * 初始化云推送通道
     */
    fun initAliPush(
        application: Application,
        channelName: String? = "",
        miAppId: String = "",
        miAppKey: String = ""
    ) {
        createNotificationChannel(application.applicationContext, channelName.str())
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

    private fun createNotificationChannel(context: Context, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 通知渠道的id
            val id = "1"
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, channelName, importance)
            // 配置通知渠道的属性
            mChannel.description = channelName
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