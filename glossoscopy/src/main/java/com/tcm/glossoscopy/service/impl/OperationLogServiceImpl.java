package com.tcm.glossoscopy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.entity.po.OperationLog;
import com.tcm.glossoscopy.mapper.OperationLogMapper;
import com.tcm.glossoscopy.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    public void saveLog(OperationLog operationLog) {
        operationLogMapper.addOperationLog(operationLog);
    }
}