package com.tcm.glossoscopy.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.entity.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获熔断限流异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BlockException.class)
    public Result handleBlockException(BlockException e) {
        log.error("异常信息：{}", e.getMessage());
//        e.printStackTrace();
        return Result.error("服务器繁忙，请稍后再试");
    }

    /**
     * 捕获业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException e){
        log.error("异常信息：{}", e.getMessage());
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    /**
     * 捕获系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler(RuntimeException e){
        log.error("异常信息：{}", e.getMessage());
        if(e instanceof AccessDeniedException){
            //捕捉权限异常，返回错误信息
            return Result.error("您没有权限进行访问");
        }
        e.printStackTrace();
        return Result.error("系统异常，请稍后再试");
    }

}
