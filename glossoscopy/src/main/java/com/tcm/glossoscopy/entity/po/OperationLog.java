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

@TableName(value="tb_operation_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;//日志ID
    private Long userId;//用户ID
    private String ipAddress;//用户IP地址
    private String requestUrl;//访问的接口路径
    private String requestParams;//请求参数
    private String operation;//操作描述
    private Boolean operationStatus;//操作状态
    private String errorMessage;//错误信息
    private LocalDateTime createTime;//操作时间
}