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
public class ChatTopicVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String memberName;
    private String doctorName;
    private LocalDateTime createTime;
}
