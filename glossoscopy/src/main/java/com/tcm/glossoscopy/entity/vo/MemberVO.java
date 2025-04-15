package com.tcm.glossoscopy.entity.vo;

import com.tcm.glossoscopy.enums.HealthStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;//成员id
    private String memberName;//成员名
    private Integer sex;//性别
    private String avatar;//头像
    private Integer age;//年龄
    private LocalDate birthday;//生日
    private HealthStatusEnum healthStatus;//体质
    private String anamnesis;//病史
    private String occupation;//职业
    private String address;//住址
}
