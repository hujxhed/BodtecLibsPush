package com.bodtec.module.push.bean;

import java.io.Serializable;

/**
 * hujx 1/28/21
 */
public class AliPushInfo implements Serializable {

    public static final String CMD_ALARM = "alarm";
    public static final String CMD_ALARM_DETAIL = "alarm_detail";
    public static final String CMD_TRUSTEESHIP = "trusteeship";
    public static final String CMD_MESSAGE_LIST = "message_list";
    public static final String CMD_DEVICE_PREVIEW = "device_preview";

    public String $commend;
    public String $action;
    public String $messageId;

}
