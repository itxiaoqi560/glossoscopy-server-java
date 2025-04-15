package com.tcm.glossoscopy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TongueSizeEnum {
    THIN("薄",0),
    THICK("厚",1);
    @JsonValue
    private final String desc;
    @EnumValue
    private final Integer value;
}
