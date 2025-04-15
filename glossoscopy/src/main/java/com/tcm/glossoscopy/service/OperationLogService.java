package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.po.OperationLog;

public interface OperationLogService extends IService<OperationLog> {


    void saveLog(OperationLog operationLog);
}