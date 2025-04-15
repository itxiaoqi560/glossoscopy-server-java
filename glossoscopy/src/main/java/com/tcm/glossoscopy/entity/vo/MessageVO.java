package com.tcm.glossoscopy.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageVO {
    private static final long serialVersionUID = 1L;
    private String content;//内容
    private String senderName;//发送者名称
    private LocalDateTime createTime;//创建时间
}
