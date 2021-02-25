package com.bodtec.module.push.receiver

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.sdk.android.push.MessageReceiver
import com.alibaba.sdk.android.push.notification.CPushMessage
import com.bodtec.module.push.ext.str
import com.bodtec.module.push.event.AliPushMessageEvent
import com.bodtec.module.push.event.AliPushNotifyEvent
import org.greenrobot.eventbus.EventBus

/**
 * hujx 2020/9/25
 */

private const val TAG_PUSH = "BodtecLibsPush"

class AliPushReceiver : MessageReceiver() {

    /**
     * 收到推送消息
     */
    override fun onMessage(context: Context?, cPushMessage: CPushMessage?) {
        super.onMessage(context, cPushMessage)
        val msg = cPushMessage?.content ?: ""
        Log.e(TAG_PUSH, "收到推送消息：$msg")
        EventBus.getDefault().post(
            AliPushMessageEvent(
                msg
            )
        )
    }

    /**
     * 收到推送通知
     */
    override fun onNotification(
        context: Context?,
        title: String?,
        summary: String?,
        extraMap: MutableMap<String, String>?
    ) {
        super.onNotification(context, title, summary, extraMap)
        doNext(
            AliPushNotifyEvent.ACTION_RECEIVE,
            title.str(),
            summary.str(),
            JSON.toJSONString(extraMap)
        )
    }

    /**
     * 打开推送通知
     */
    override fun onNotificationOpened(
        context: Context?,
        title: String?,
        summary: String?,
        extraMap: String?
    ) {
        super.onNotificationOpened(context, title, summary, extraMap)
        doNext(
            AliPushNotifyEvent.ACTION_OPEN,
            title.str(),
            summary.str(),
            extraMap.str()
        )
    }

    override fun onNotificationClickedWithNoAction(
        context: Context?,
        title: String?,
        summary: String?,
        extraMap: String?
    ) {
        super.onNotificationClickedWithNoAction(context, title, summary, extraMap)
        doNext(
            AliPushNotifyEvent.ACTION_OPEN,
            title.str(),
            summary.str(),
            extraMap.str()
        )
    }

    private fun doNext(
        type: Int,
        title: String,
        summary: String,
        extraMap: String
    ) {
        val typeStr = if (type == AliPushNotifyEvent.ACTION_RECEIVE) "接收通知：" else "打开通知："
        Log.e(TAG_PUSH, "type=$typeStr,title=$title,summary=$summary,extraMap=$extraMap")
        EventBus.getDefault().post(
            AliPushNotifyEvent(
                type,
                title,
                summary,
                extraMap
            )
        )
    }

}