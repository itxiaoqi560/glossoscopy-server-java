package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户评价信息")
public class UserReviewDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "接收者UUID")
    private String receiverUUID;
    @ApiModelProperty(value = "评分")
    private Integer rating;
    @ApiModelProperty(value = "评价")
    private String comment;
}
