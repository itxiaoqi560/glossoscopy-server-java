package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel(description = "成员信息")
public class MemberDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "成员id")
    private Long id;
    @ApiModelProperty(value = "成员名")
    private String memberName;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;
    @ApiModelProperty(value = "病史")
    private String anamnesis;
    @ApiModelProperty(value = "职业")
    private String occupation;
    @ApiModelProperty(value = "住址")
    private String address;
}
