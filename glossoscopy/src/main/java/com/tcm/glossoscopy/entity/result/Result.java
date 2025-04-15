package com.tcm.glossoscopy.entity.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private DataObject<T> data; //数据

    public static <T> Result<T> success() {
        return new Result<>(1, "请求成功", new DataObject<>(null,null));
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "请求成功", new DataObject<>(data,null));
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, new DataObject<>(null,null));
    }

    public static <T> Result<T> successWithToken(T data){
        String token = getToken();
        return new Result<>(1,"请求成功",new DataObject<>(data,token));
    }

    public static <T> Result<T> successWithToken(){
        String token = getToken();
        return new Result<>(1,"请求成功",new DataObject<>(null,token));
    }

    @Data
    @AllArgsConstructor
    //添加该注解，忽略null值字段
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataObject<T> implements Serializable {
        //手动指定serialVersionUID
        private static final long serialVersionUID = 1L;
        T data;
        String token;
    }

    private static String getToken(){
        // 构建用户信息映射
        Map<String, Object> mp = new HashMap<>();
        mp.put(Constant.ID, UserIdContext.getId());
        // 生成 jwt 令牌
        String token = JwtUtil.createJWT(Constant.SECRET_KEY, RedisConstant.TOKEN_EXPIRE, mp);
        return token;
    }
}