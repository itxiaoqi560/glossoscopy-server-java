package com.tcm.glossoscopy.constant;

public abstract class RegexPatterns {
    /**
     * 手机号正则
     */
    public static final String PHONE_NUMBER_REGEX = "^1([3-9])\\d{9}$";
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    /**
     * 账号正则，8~20位的字母、数字、下划线
     */
    public static final String ACCOUNT_REGEX = "^[a-zA-Z\\d_]{8,20}$";
    /**
     * 密码正则，8~20位的字母、数字、特殊字符
     */
    public static final String PASSWORD_REGEX = "^[a-zA-Z\\d_!@#$%^&*(),.?\":{}|<>]{8,20}$";
}