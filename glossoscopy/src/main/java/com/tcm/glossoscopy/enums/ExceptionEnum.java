package com.tcm.glossoscopy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    PHONE_NUMBER_ALREADY_REGISTERED("手机号已被注册"),
    DYNAMIC_CODE_ERROR("动态码有误"),
    GET_DYNAMIC_CODE_FAILED("获取动态码失败"),
    FILE_UPLOAD_FAILED("文件上传失败"),
    GET_DIAGNOSIS_DATA_FAILED("获取诊断数据失败"),
    ACCOUNT_OR_PASSWORD_ERROR("账号或密码错误"),
    PASSWORD_ERROR("密码错误"),
    ACCOUNT_ALREADY_EXIST("账号已存在"),
    TOKEN_PARSING_FAILED("令牌解析失败"),
    USER_NOT_LOGGED_IN("用户还未登陆"),
    PHONE_NUMBER_FORMAT_IS_INCORRECT("手机号格式有误"),
    ACCOUNT_FORMAT_IS_INCORRECT("账号格式有误"),
    EMAIL_FORMAT_IS_INCORRECT("邮箱格式有误"),
    PASSWORD_FORMAT_IS_INCORRECT("密码格式有误"),
    THE_SERVER_IS_BUSY("服务器繁忙"),
    CAPTCHA_GENERATION_FAILED("校验码生成失败"),
    CAPTCHA_ERROR("校验码错误"),
    ACCOUNT_IS_ABNORMAL("账号异常"),
    REQUEST_TOO_FREQUENT("请求过于频繁"),
    MEMBER_HAS_NO_RECORDS_IN_PAST_SEVEN_DAY("该成员近七天没有诊断记录"),
    NO_DOCTORS_AVAILABLE_FOR_CONSULTATION("暂时没有可问诊的医生");

    private final String message;
}
