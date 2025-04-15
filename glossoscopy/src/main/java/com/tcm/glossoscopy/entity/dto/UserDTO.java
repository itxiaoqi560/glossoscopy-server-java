package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户信息")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "手机动态码")
    private String phoneCode;
    @ApiModelProperty(value = "uuid")
    private String uuid;
    @ApiModelProperty(value = "校验码")
    private String captcha;
}
