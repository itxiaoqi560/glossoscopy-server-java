package com.tcm.glossoscopy.utils;

import java.lang.reflect.Method;

public class EnumUtil {
    public static<T> T fromValue(Class<T> cla,Integer value){
        for (T enumInstance : cla.getEnumConstants()) {
            try {
                Method getValue = cla.getMethod("getValue");
                if (getValue.invoke(enumInstance).equals(value)) {
                    return enumInstance;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 未找到对应的枚举实例时，可根据实际情况抛出异常或返回 null
        return null;
    }
}
