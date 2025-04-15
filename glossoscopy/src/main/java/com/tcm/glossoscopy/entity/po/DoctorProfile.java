package com.tcm.glossoscopy.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value="tb_doctor_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long doctorId;
    private String title;
    private String introduction;
    private String specialty;
    private String hospital;
    private Integer workExperience;
    private String education;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
