package com.tcm.glossoscopy.service;


import com.tcm.glossoscopy.entity.vo.HealthStatusReportVO;
import com.tcm.glossoscopy.entity.vo.RecordReportVO;

public interface ReportService {
    /**
     * 统计成员体质分布数据
     *
     * @return
     */
    HealthStatusReportVO getHealthStatusStatistics();

    /**
     * 统计近七天诊断记录分布数据
     *
     * @return
     */
    RecordReportVO getRecordStatistics();

}
