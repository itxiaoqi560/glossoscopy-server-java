package com.tcm.glossoscopy.constant;

import java.util.List;
import java.util.Objects;

public abstract class RedisConstant {
    public static final String LOGIN_KEY = "TCM:LOGIN:";
    public static final String COUNT_KEY = "TCM:COUNT:";
    public static final String CAPTCHA_KEY = "TCM:CAPTCHA:";
    public static final String PHONE_CODE_PREFIX_KEY = "TCM:CODE:";
    public static final String MEMBER_KEY = "TCM:MEMBER:";
    public static final String LOCK_KEY = "TCM:LOCK:";
    public static final Long CAPTCHA_EXPIRE = 5L;//计时单位：分钟
    public static final Long PHONE_CODE_EXPIRE = 5L;//计时单位：分钟
    public static final Long MEMBER_EXPIRE = 5L;//计时单位：分钟
    public static final Long LOGIN_EXPIRE = 1L;//计时单位：天
    public static final Long TOKEN_EXPIRE = 24 * 60 * 60 * 1000L;//计时单位：毫秒
    public static final String CHAT_KEY = "TCM:CHAT:";
    public static final String CHAT_DOCTOR_KEY = "TCM:CHAT:DOCTOR:";
    public static final String CHAT_USER_KEY = "TCM:CHAT:USER:";
    public static final Long CHAT_EXPIRE =1L ;//计时单位：天
    public static final String CHAT_TOPIC_KEY = "TCM:CHAT:TOPIC:";
    public static final String CHAT_RECORD_KEY = "TCM:CHAT:RECORD:";
    public static final String CHAT_PROFILE_KEY = "TCM:CHAT:PROFILE:";
    public static final String CHAT_ACCEPT_KEY = "TCM:CHAT:ACCEPT:";
    public static final Long CHAT_ACCEPT_EXPIRE = 5L;
    public static final String CHAT_CONSULTATION_KEY = "TCM:CONSULTATION:";
}
