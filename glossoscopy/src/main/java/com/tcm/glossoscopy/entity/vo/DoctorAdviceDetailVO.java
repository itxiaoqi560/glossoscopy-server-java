package com.tcm.glossoscopy.entity.vo;

import com.tcm.glossoscopy.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAdviceDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String advice;
    private LocalDateTime createTime;
    private Integer age;//年龄
    private Integer sex;//性别
    private String image;//图片
    private ToothMarkEnum toothMark;//齿痕
    private TongueThicknessEnum tongueThickness;//厚薄
    private TongueSizeEnum tongueSize;//胖瘦
    private CoatingColorEnum coatingColor;//苔色
    private HealthStatusEnum healthStatus;//体质
}
