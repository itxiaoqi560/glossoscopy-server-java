package com.tcm.glossoscopy.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户反馈DTO")
public class UserFeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;
}
