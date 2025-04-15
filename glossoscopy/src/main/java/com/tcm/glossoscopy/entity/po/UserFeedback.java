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

@TableName(value="tb_user_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFeedback implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String type;
    private String content;
    private String phoneNumber;
    private Boolean deleteFlag;
    private LocalDateTime createTime;
}
