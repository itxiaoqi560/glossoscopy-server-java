package com.tcm.glossoscopy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaptchaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String captcha;
}
