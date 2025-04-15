package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.RecordCrudeVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
@Api(tags = "诊断记录接口")
@RequestMapping("/api/record")
@Slf4j
public class RecordController {

    @Resource
    private RecordService recordService;

    /**
     * 根据记录id批量删除诊断记录
     *
     * @param idListDTO
     * @return
     */
    @DeleteMapping("/batchDeleteRecord")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @ApiOperation("批量删除诊断记录")
    @Loggable(value = "批量删除诊断记录")
    public Result batchDeleteRecord(@RequestBody IdListDTO idListDTO) {
        log.info("批量删除诊断记录：{},{}", UserIdContext.getId(), idListDTO);
        recordService.batchDeleteRecord(idListDTO);
        return Result.success();
    }

    /**
     * 分页查询历史诊断记录
     *
     * @param page
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param memberName
     * @return
     */
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @GetMapping("/pageQueryUserRecord")
    @ApiOperation("分页查询历史诊断记录")
    @Loggable(value = "分页查询历史诊断记录")
    public Result pageQueryUserRecord(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime,
                                        @RequestParam(required = false) String memberName) {
        log.info("分页查询历史诊断记录：{},{},{},{},{},{}", UserIdContext.getId(), page, pageSize, beginTime, endTime, memberName);
        PageResult pageResult = recordService.pageQueryUserRecord(page, pageSize, beginTime, endTime, memberName);
        return Result.success(pageResult);
    }

    /**
     * 根据记录id获取详细诊断记录
     *
     * @param id
     * @return
     */
    @GetMapping("/getRecordDetailInfo")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @ApiOperation("获取详细诊断记录")
    @Loggable(value = "获取详细诊断记录")
    public Result getRecordDetailInfo(Long id) {
        log.info("获取详细诊断记录：{},{}", UserIdContext.getId(), id);
        RecordDetailVO recordDetailVO = recordService.getRecordDetailInfo(id);
        return Result.success(recordDetailVO);
    }

    /**
     * 成员诊断
     *
     * @param memberId
     * @param url
     * @return
     */
    @GetMapping("/diagnose")
    @PreAuthorize("hasAuthority('TCM:FAMILY')")
    @ApiOperation("成员诊断")
    @Loggable(value = "成员诊断")
    public Result diagnose(Long memberId, String url) {
        log.info("成员诊断：{},{},{}", UserIdContext.getId(), memberId, url);
        RecordDetailVO recordDetailVO = recordService.diagnose(memberId, url);
        return Result.success(recordDetailVO);
    }

    /**
     * 医生随机获取用户诊断记录
     *
     * @return
     */
    @GetMapping("/getRandomRecord")
    @PreAuthorize("hasAuthority('TCM:DOCTOR')")
    @ApiOperation("医生随机获取用户诊断记录")
    @Loggable(value = "医生随机获取用户诊断记录")
    public Result getRandomRecord() {
        log.info("医生随机获取用户诊断记录：{}", UserIdContext.getId());
        RecordCrudeVO recordCrudeVO = recordService.getRandomRecord();
        return Result.success(recordCrudeVO);
    }

}
