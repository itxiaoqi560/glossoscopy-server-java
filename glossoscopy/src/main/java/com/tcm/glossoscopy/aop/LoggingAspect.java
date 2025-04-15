package com.tcm.glossoscopy.aop;

import cn.hutool.json.JSONUtil;
import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.po.OperationLog;
import com.tcm.glossoscopy.service.OperationLogService;
import com.tcm.glossoscopy.utils.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {

    @Resource
    private OperationLogService operationLogService;
    @Resource
    private IpUtil ipUtil;

    @Pointcut("@annotation(com.tcm.glossoscopy.annotation.Loggable)")
    public void logPointcut() {}

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        saveLog(joinPoint, true, "");
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        saveLog(joinPoint, false, ex.getMessage());
    }

    private void saveLog(JoinPoint joinPoint, boolean operationStatus, String errorMessage) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String operation = signature.getMethod().getAnnotation(Loggable.class).value();
        OperationLog operationLog = OperationLog.builder()
                .userId(UserIdContext.getId())
                .ipAddress(ipUtil.getClientIp(request))
                .requestParams(getRequestParams(joinPoint))
                .requestUrl(request.getRequestURI())
                .operation(operation)
                .operationStatus(operationStatus)
                .errorMessage(errorMessage)
                .createTime(LocalDateTime.now())
                .build();
        operationLogService.saveLog(operationLog);
    }

    private String getRequestParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return JSONUtil.toJsonStr(args);
    }
}