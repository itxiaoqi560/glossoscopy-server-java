package com.tcm.glossoscopy.entity.bo;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisData<T> {
    private T data;//缓存数据
    private LocalDateTime expireTime;//过期时间
}