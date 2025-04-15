package com.tcm.glossoscopy.utils;

import com.tcm.glossoscopy.constant.RegexPatterns;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;

import java.util.Objects;

public class RegexUtil {
    /**
     * 验证手机号格式是否合法
     * @param phoneNumber 待校验的手机号
     */
    public static void isPhoneNumberValid(String phoneNumber){
        if(mismatch(phoneNumber,RegexPatterns.PHONE_NUMBER_REGEX)){
            throw new BusinessException(ExceptionEnum.PHONE_NUMBER_FORMAT_IS_INCORRECT);
        }
    }

    /**
     * 验证邮箱格式是否合法
     * @param email 待校验的邮箱
     */
    public static void isEmailValid(String email){
        if(mismatch(email,RegexPatterns.EMAIL_REGEX)){
            throw new BusinessException(ExceptionEnum.EMAIL_FORMAT_IS_INCORRECT);
        }
    }

    /**
     * 验证账号格式是否合法
     * @param account 待验证的账号
     */
    public static void isAccountValid(String account) {
        if(mismatch(account,RegexPatterns.ACCOUNT_REGEX)){
            throw new BusinessException(ExceptionEnum.ACCOUNT_FORMAT_IS_INCORRECT);
        }
    }

    /**
     * 验证密码格式是否合法
     * @param password 待验证的密码
     */
    public static void isPasswordValid(String password) {
        if(mismatch(password,RegexPatterns.PASSWORD_REGEX)){
            throw new BusinessException(ExceptionEnum.PASSWORD_FORMAT_IS_INCORRECT);
        }
    }


    private static boolean mismatch(String str, String regex){
        if (Objects.isNull(str)) {
            return true;
        }
        return !str.matches(regex);
    }

}