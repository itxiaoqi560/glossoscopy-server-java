package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.DoctorAdviceDTO;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.UserFeedbackDTO;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.service.DoctorAdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
@Api(tags = "医生建议接口")
@RequestMapping("/api/doctorAdvice")
@Slf4j
public class DoctorAdviceController {
    @Resource
    private DoctorAdviceService doctorAdviceService;

    /**
     * 新增医生建议
     *
     * @param doctorAdviceDTO
     * @return
     */
    @PostMapping("/addDoctorAdvice")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("新增医生建议")
    @Loggable(value = "新增医生建议")
    public Result addFeedback(@RequestBody DoctorAdviceDTO doctorAdviceDTO) {
        log.info("新增医生建议：{},{}", UserIdContext.getId(), doctorAdviceDTO);
        doctorAdviceService.addDoctorAdvice(doctorAdviceDTO);
        return Result.success();
    }

    /**
     * 分页查询医生建议
     *
     * @param page
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/pageQueryDoctorAdvice")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("分页查询医生建议")
    @Loggable(value = "分页查询医生建议")
    public Result pageQueryDoctorAdvice(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        log.info("分页查询医生建议：{},{},{},{},{}", UserIdContext.getId(), page, pageSize, beginTime, endTime);
        PageResult pageResult = doctorAdviceService.pageQueryDoctorAdvice(page, pageSize, beginTime, endTime);
        return Result.success(pageResult);
    }

    /**
     * 批量删除医生建议
     *
     * @param idListDTO
     * @return
     */
    @DeleteMapping("/batchDeleteDoctorAdvice")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("批量删除医生建议")
    @Loggable(value = "批量删除医生建议")
    public Result batchDeleteDoctorAdvice(@RequestBody IdListDTO idListDTO) {
        log.info("批量删除医生建议：{},{}", UserIdContext.getId(), idListDTO);
        doctorAdviceService.batchDeleteDoctorAdvice(idListDTO);
        return Result.success();
    }

    /**
     * 修改医生建议
     *
     * @param doctorAdviceDTO
     * @return
     */
    @PutMapping("/updateDoctorAdvice")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("修改医生建议")
    @Loggable(value = "修改医生建议")
    public Result updateDoctorAdvice(@RequestBody DoctorAdviceDTO doctorAdviceDTO) {
        log.info("修改医生建议：{},{}", UserIdContext.getId(), doctorAdviceDTO);
        doctorAdviceService.updateDoctorAdvice(doctorAdviceDTO);
        return Result.success();
    }
}
