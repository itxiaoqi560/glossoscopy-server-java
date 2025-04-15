package com.tcm.glossoscopy.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HealthStatusEnum {
    NONE_FOR_NOW("暂无",0),
    BALANCED_CONSTITUTION("平和体质",1),
    YANG_DEFICIENCY_CONSTITUTION("阳虚体质",2),
    YIN_DEFICIENCY_CONSTITUTION("阴虚体质",3),
    QI_DEFICIENCY_CONSTITUTION("气虚体质",4),
    PHLEGM_DAMPNESS_CONSTITUTION("痰湿体质",5),
    DAMP_HEAT_CONSTITUTION("湿热体质",6),
    BLOOD_STASIS_CONSTITUTION("血瘀体质",7),
    QI_STAGNATION_CONSTITUTION("气郁体质",8),
    IDIOSYNCRATIC_CONSTITUTION("特禀体质（过敏体质）",9);
    @JsonValue
    private final String desc;
    @EnumValue
    private final Integer value;
}
