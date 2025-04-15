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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//用户id
    private String username;//用户名
    private String avatar;//头像
    private String account;//账号
    private String phoneNumber;//手机号
    private String password;//密码
    private Boolean status;//是否可用
    private Boolean deleteFlag;//是否删除
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//修改时间
}
