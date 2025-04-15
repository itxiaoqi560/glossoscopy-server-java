package com.tcm.glossoscopy.handler;

import com.alibaba.fastjson.JSON;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.management.relation.RelationSupport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//这个类只处理认证异常，不处理授权异常
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    //第一个参数是请求对象，第二个参数是响应对象，第三个参数是异常对象。把异常封装成授权的对象，然后封装到handle方法
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = Result.error("用户认证失败，请重新登录");
        //将result对象转换为JSON字符串
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response,json);
    }
}