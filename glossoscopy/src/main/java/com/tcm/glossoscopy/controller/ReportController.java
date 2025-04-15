package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.HealthStatusReportVO;
import com.tcm.glossoscopy.entity.vo.RecordReportVO;
import com.tcm.glossoscopy.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "报表接口")
@RequestMapping("/api/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * 统计成员体质分布数据
     *
     * @return
     */
    @GetMapping("/getHealthStatusStatistics")
    @ApiOperation("统计成员体质分布数据")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @Loggable(value = "统计成员体质分布数据")
    public Result<HealthStatusReportVO> getHealthStatusStatistics() {
        log.info("统计成员体质分布数据：{}", UserIdContext.getId());
        HealthStatusReportVO healthStatusReportVO = reportService.getHealthStatusStatistics();
        return Result.success(healthStatusReportVO);
    }

    /**
     * 统计近七天诊断记录分布数据
     *
     * @return
     */
    @GetMapping("/getRecordStatistics")
    @ApiOperation("统计近七天诊断记录分布数据")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @Loggable(value = "统计近七天诊断记录分布数据")
    public Result<RecordReportVO> getRecordStatistics() {
        log.info("统计近七天诊断记录分布数据：{}", UserIdContext.getId());
        RecordReportVO recordReportVO = reportService.getRecordStatistics();
        return Result.success(recordReportVO);
    }
}
