package com.tcm.glossoscopy.cache;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSONObject;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.entity.bo.RedisData;
import io.jsonwebtoken.lang.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class RedisCache {
    @Resource
    private RedisTemplate redisTemplate;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    public <T> T execute(DefaultRedisScript<T> script, List<String> keys, String... args) {
        return (T) redisTemplate.execute(script, keys, args);
    }

    public <T> void save(String key, T object, Long ttl, TimeUnit timeUnit) {
        String json = JSONObject.toJSONString(object);
        redisTemplate.opsForValue().set(key, json, ttl, timeUnit);
    }

    public <T> T get(String key, Class<T> cla) {
        String json = (String) redisTemplate.opsForValue().get(key);
        T result = null;
        if (Strings.hasText(json)) {
            result = JSONObject.parseObject(json, cla);
        }
        return result;
    }

    public void saveCount(String key, Long ttl, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, 1, ttl, timeUnit);
    }

    public Integer getCount(String key) {
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        return count;
    }

    public void inc(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void inc(String key,Long value) {
        redisTemplate.opsForValue().increment(key,value);
    }

    public <T> void saveCollection(String key, Collection<T> collection, Long ttl, TimeUnit timeUnit) {
        // 将集合转换为 JSON 字符串
        String json = JSONObject.toJSONString(collection);
        // 将 JSON 字符串存储到 Redis 中，并设置过期时间
        redisTemplate.opsForValue().set(key, json, ttl, timeUnit);
    }

    public <T> List<T> getCollection(String key, Class<T> cla) {
        String json = (String) redisTemplate.opsForValue().get(key);
        List<T> result = null;
        if (Strings.hasText(json)) {
            result = JSONObject.parseArray(json, cla);
        }
        return result;
    }

    public void expire(String key, Long ttl, TimeUnit timeUnit) {
        redisTemplate.expire(key, ttl, timeUnit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }


    public <T> void saveRedisData(String key, T object, Long ttl, TimeUnit timeUnit) {
        RedisData redisData = new RedisData(object, LocalDateTime.now().plusSeconds(timeUnit.toSeconds(ttl)));
        save(key, redisData, ttl, timeUnit);
    }

    public void saveNullObject(String key) {
        redisTemplate.opsForValue().set(key, "", 3L, TimeUnit.MINUTES);
    }

    //缓存穿透
    public <T> T queryWithPassThrough(String keyPre, Long id, Class<T> cla, Function<Long, T> callback, Long ttl, TimeUnit timeUnit) {
        String key = keyPre + id;
        String json = (String) redisTemplate.opsForValue().get(key);
        if (!Objects.isNull(json)) {
            if (Strings.hasText(json)) {
                return JSONObject.parseObject(json, cla);
            }
            return null;
        }
        T object = callback.apply(id);
        if (Objects.isNull(object)) {
            saveNullObject(key);
            return null;
        }
        save(key, object, ttl, timeUnit);
        return object;
    }


    //缓存击穿，应用该方法要确保热点在项目启动时必须要缓存到内存中，否则无法访问热点key
    public <T> T queryWithLogicExpire(String keyPre, Long id, Class<T> cla, Function<Long, T> callback, Long ttl, TimeUnit timeUnit) {
        String key = keyPre + id;
        String str = (String) redisTemplate.opsForValue().get(key);
        if (!Strings.hasText(str)) {
            return null;
        }
        RedisData redisData = JSONObject.parseObject(str, RedisData.class);
        LocalDateTime expireTime = redisData.getExpireTime();
        if (expireTime.isAfter(LocalDateTime.now())) {
            return (T) redisData.getData();
        }
        String lockKey = RedisConstant.LOCK_KEY + id;
        boolean isLock = tryLock(lockKey);
        if (isLock) {
            str = (String) redisTemplate.opsForValue().get(key);
            redisData = JSONObject.parseObject(str, RedisData.class);
            expireTime = redisData.getExpireTime();
            if (expireTime.isBefore(LocalDateTime.now())) {
                executorService.submit(() -> {
                    try {
                        T object = callback.apply(id);
                        saveRedisData(key, object, ttl, timeUnit);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        unLock(key);
                    }
                });
            }
        }
        return (T) redisData.getData();
    }


    private boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10L, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unLock(String key) {
        delete(key);
    }
}
