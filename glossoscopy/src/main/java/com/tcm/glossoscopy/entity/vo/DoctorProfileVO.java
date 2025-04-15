package com.tcm.glossoscopy.entity.vo;

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
public class DoctorProfileVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String introduction;
    private String specialty;
    private String hospital;
    private Integer workExperience;
    private String education;
    private LocalDateTime updateTime;
}
