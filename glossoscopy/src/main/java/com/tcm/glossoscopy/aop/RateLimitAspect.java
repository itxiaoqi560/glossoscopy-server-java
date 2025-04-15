package com.tcm.glossoscopy.aop;

import com.tcm.glossoscopy.annotation.RateLimit;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.utils.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitAspect {

    @Resource
    private RedisCache redisCache;
    @Resource
    private IpUtil ipUtil;

    @Pointcut("@annotation(com.tcm.glossoscopy.annotation.RateLimit)")
    public void rateLimitPointcut() {}

    @Around("rateLimitPointcut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip=ipUtil.getClientIp(request);
        String key = RedisConstant.COUNT_KEY + ip.hashCode() + ":" + request.getRequestURI();
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        RateLimit rateLimit = methodSignature.getMethod().getAnnotation(RateLimit.class);
        Integer count = redisCache.getCount(key);
        if (Objects.isNull(count)) {
            redisCache.saveCount(key, (long) rateLimit.timeout(), TimeUnit.SECONDS);
        } else if (count < rateLimit.limit()) {
            redisCache.inc(key);
        } else {
            throw new BusinessException(ExceptionEnum.REQUEST_TOO_FREQUENT);
        }
        return joinPoint.proceed();
    }

}