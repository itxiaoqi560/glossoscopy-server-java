package com.tcm.glossoscopy.entity.po;

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
public class RecordCrude implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;//记录id
    private String memberName;//成员名
    private Integer age;//年龄
    private Integer sex;//性别
    private String image;//图片
    private ToothMarkEnum toothMark;//齿痕
    private TongueThicknessEnum tongueThickness;//厚薄
    private TongueSizeEnum tongueSize;//胖瘦
    private CoatingColorEnum coatingColor;//苔色
    private HealthStatusEnum healthStatus;//体质
    private LocalDateTime createTime;//诊断时间
}
