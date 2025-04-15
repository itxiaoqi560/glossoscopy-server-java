package com.tcm.glossoscopy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ToothMarkEnum {
    NORMAL("正常",0),
    TOOTH_MARK("有齿痕",1);
    @JsonValue
    private final String desc;
    @EnumValue
    private final Integer value;
}
