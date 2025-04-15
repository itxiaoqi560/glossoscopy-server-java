package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(description = "医生简历DTO")
public class DoctorProfileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "职称")
    private String title;
    @ApiModelProperty(value = "简历")
    private String introduction;
    @ApiModelProperty(value = "专业领域")
    private String specialty;
    @ApiModelProperty(value = "所属医院")
    private String hospital;
    @ApiModelProperty(value = "工作年限")
    private Integer workExperience;
    @ApiModelProperty(value = "教育背景")
    private String education;
}
