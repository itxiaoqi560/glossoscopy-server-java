package com.tcm.glossoscopy.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("tb_record")
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//记录id
    private Long userId;//用户id
    private Long memberId;//成员id
    private String image;//图片
    private ToothMarkEnum toothMark;//齿痕
    private TongueThicknessEnum tongueThickness;//厚薄
    private TongueSizeEnum tongueSize;//胖瘦
    private CoatingColorEnum coatingColor;//苔色
    private HealthStatusEnum healthStatus;//体质
    private Boolean deleteFlag;//是否删除
    private LocalDateTime createTime;//诊断时间
}
