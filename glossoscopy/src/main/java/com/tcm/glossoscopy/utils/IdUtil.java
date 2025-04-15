package com.tcm.glossoscopy.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class IdUtil {

    private static final long BEGIN_TIMESTAMP=1640995200L;
    private static final int COUNT_BIT=32;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public IdUtil(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }

    public long nextId(String keyPrefix){
        LocalDateTime now = LocalDateTime.now();
        long nowSecond=now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp=nowSecond-BEGIN_TIMESTAMP;

        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        long count=stringRedisTemplate.opsForValue().increment("icr:"+keyPrefix+":"+date);
        return timeStamp<<COUNT_BIT|count;
    }

}
