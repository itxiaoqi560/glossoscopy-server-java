package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "问诊DTO")
public class ConsultationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "接收者UUID")
    private String receiverUUID;
    @ApiModelProperty(value = "发送者UUID")
    private String senderUUID;
    @ApiModelProperty(value = "成员id")
    private Long memberId;
    @ApiModelProperty(value = "消息")
    private String content;
}
