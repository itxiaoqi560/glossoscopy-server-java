package com.tcm.glossoscopy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoatingColorEnum {
    WHITE("白色",0),
    LIGHT_YELLOW("淡黄色",1),
    YELLOW("黄色",2),
    GREY("灰色",3),
    BLACK("黑色",4);
    @JsonValue
    private final String desc;
    @EnumValue
    private final Integer value;
}
