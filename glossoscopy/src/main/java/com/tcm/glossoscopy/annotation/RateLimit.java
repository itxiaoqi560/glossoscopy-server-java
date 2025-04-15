package com.tcm.glossoscopy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 注解作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface RateLimit {
    int limit();
    int timeout();
}