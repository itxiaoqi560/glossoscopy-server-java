package com.tcm.glossoscopy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;//用户名
    private String phoneNumber;//手机号
    private String account;//账号
    private String avatar;//头像
    private Boolean isFirstLogin;//是否是第一次登录
}
