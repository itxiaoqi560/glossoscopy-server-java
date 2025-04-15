package com.tcm.glossoscopy.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName(value="tb_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long userId;
    private Long roleId;
}