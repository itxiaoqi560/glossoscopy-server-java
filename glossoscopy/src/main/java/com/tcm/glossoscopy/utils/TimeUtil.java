package com.tcm.glossoscopy.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    public static long getMillInstance(){
        //获取当前时间（中国时区）
        ZonedDateTime chinaTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        //转换为毫秒级时间戳
        long timestampSeconds = chinaTime.toInstant().toEpochMilli();
        return timestampSeconds;
    }
}
