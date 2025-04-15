package com.tcm.glossoscopy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcm.glossoscopy.entity.po.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    @Insert("insert into tb_operation_log (user_id, ip_address, request_url, request_params,operation, operation_status, error_message, create_time) " +
            "value(#{userId}, #{ipAddress}, #{requestUrl}, #{requestParams},#{operation}, #{operationStatus}, #{errorMessage}, #{createTime})")
    void addOperationLog(OperationLog operationLog);
}