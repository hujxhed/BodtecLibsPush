package com.bodtec.libs.push.event

/**
 * hujx 2020/9/25
 */
class AliPushNotifyEvent constructor(
        var action: Int,// 收到通知 or 打开通知
        var title: String,
        var summary: String,
        var extraMap: String
) {
    companion object {
        const val ACTION_RECEIVE = 1     //收到通知
        const val ACTION_OPEN = 2        //打开通知
    }
}