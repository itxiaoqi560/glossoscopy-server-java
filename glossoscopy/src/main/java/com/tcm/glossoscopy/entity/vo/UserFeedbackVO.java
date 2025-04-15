package com.tcm.glossoscopy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedbackVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String content;
    private String phoneNumber;
    private LocalDateTime createTime;
}
