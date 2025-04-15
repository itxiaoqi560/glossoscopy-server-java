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

@TableName(value="tb_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long roleId;
    private Long menuId;
}