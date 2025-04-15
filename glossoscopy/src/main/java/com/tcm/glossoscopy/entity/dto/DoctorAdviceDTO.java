package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "医生建议DTO")
public class DoctorAdviceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "医生建议id")
    private Long id;
    @ApiModelProperty(value = "诊断记录id")
    private Long recordId;
    @ApiModelProperty(value = "建议")
    private String advice;
}
