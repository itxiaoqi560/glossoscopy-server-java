package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "聊天主题DTO")
public class ChatTopicDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主题id")
    private Long id;
}
