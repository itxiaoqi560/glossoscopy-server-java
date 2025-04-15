package com.tcm.glossoscopy.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tcm.glossoscopy.enums.HealthStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor


@NoArgsConstructor
@Builder
@TableName("tb_health_status")
public class HealthStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//体质
    private String description;//描述
    private String externalCharacteristics;//外在特征
    private String intrinsicCharacteristics;//内在特征
    private String tendencyToFallIll;//发病趋势
    private String psychologicalConditioning;//心理调理
    private String exerciseConditioning;//运动调理
    private String traditionalChineseMedicineConditioning;//中医药调理
    private String dietaryConditioning;//饮食调理
    private String contraindication;//禁忌
}
