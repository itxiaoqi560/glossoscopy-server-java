package com.tcm.glossoscopy.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.tcm.glossoscopy.enums.HealthStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_member")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//成员id
    private Long userId;//用户id
    private String memberName;//成员名
    private Integer sex;//性别
    private String avatar;//头像
    private LocalDate birthday;//生日
    private HealthStatusEnum healthStatus;//体质
    private String anamnesis;//病史
    private String occupation;//职业
    private String address;//住址
    private Boolean deleteFlag;//是否删除
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//修改时间
}
