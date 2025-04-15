package com.tcm.glossoscopy.filter;

import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.bo.LoginUser;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter implements HandshakeInterceptor {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(Constant.TOKEN);
        //校验用户身份信息
        //!Strings.hasText()方法用于检查给定的字符串是否为空或仅包含空格字符
        if (!Strings.hasText(token)) {
            //如果请求没有携带token，那么就不需要解析token，不需要获取用户信息，直接放行就可以
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        Long userId;
        try {
            Claims claims = JwtUtil.parseJWT(Constant.SECRET_KEY,token);
            userId = Long.valueOf((Integer) claims.get(Constant.ID));
            UserIdContext.setId(userId);
        } catch (Exception e) {
            log.error("jwt令牌解析失败");
            throw new BusinessException(ExceptionEnum.TOKEN_PARSING_FAILED);
        }
        //从redis中获取用户信息
        String loginKey = RedisConstant.LOGIN_KEY + userId;
        LoginUser loginUser = redisCache.get(loginKey, LoginUser.class);
        //判断获取到的用户信息是否为空，因为redis里面可能并不存在这个用户信息，例如缓存过期了
        if(Objects.isNull(loginUser)){
            log.error("用户还未登录");
            //抛出一个异常
            throw new BusinessException(ExceptionEnum.USER_NOT_LOGGED_IN);
        }
        //把最终的LoginUser用户信息，通过setAuthentication方法，存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                //第一个参数是LoginUser用户信息，第二个参数是凭证，第三个参数是权限信息
                new UsernamePasswordAuthenticationToken(loginUser,null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //全部做完之后，就放行
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}