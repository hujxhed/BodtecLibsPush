package com.bodtec.module.push.act

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.alibaba.sdk.android.push.AndroidPopupActivity
import com.blankj.utilcode.util.SPUtils
import com.bodtec.module.push.BodtecPushConst
import com.bodtec.module.push.R

class AliPushPopupAct : AndroidPopupActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.act_ali_push_popup)
    }

    // 实现通知打开回调方法，获取通知相关信息
    // @param 标题
    // @param 内容
    // @param 额外参数
    override fun onSysNoticeOpened(
        title: String,
        content: String,
        extMap: Map<String, String>
    ) {
        //Log.e(TAG, "title: $title, content: $content, extMap: ${Gson().toJson(extMap)}")
        try {
            val splashPath = SPUtils.getInstance().getString(BodtecPushConst.KEY_SPLASH_PAGE)
            val messageId = extMap["\$messageId"]
            val action = extMap["\$action"]
            val clazz = Class.forName(splashPath)
            val mIntent = Intent(this@AliPushPopupAct, clazz)
            mIntent.putExtra("param_1", messageId)
            mIntent.putExtra("param_2", action)
            startActivity(mIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}