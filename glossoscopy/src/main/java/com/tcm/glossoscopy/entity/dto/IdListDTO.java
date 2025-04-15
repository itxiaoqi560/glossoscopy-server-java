package com.tcm.glossoscopy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "id集合信息")
public class IdListDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id集合")
    private List<Long> idList;
}
