package com.tcm.glossoscopy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TongueThicknessEnum {
    NORMAL("正常",0),
    FAT("胖",1),
    THIN("瘦",2);
    @JsonValue
    private final String desc;
    @EnumValue
    private final Integer value;
}
