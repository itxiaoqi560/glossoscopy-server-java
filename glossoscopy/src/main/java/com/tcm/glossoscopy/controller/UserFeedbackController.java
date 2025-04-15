package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.dto.UserFeedbackDTO;
import com.tcm.glossoscopy.entity.po.UserFeedback;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.UserFeedbackVO;
import com.tcm.glossoscopy.service.UserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "用户反馈接口")
@RequestMapping("/api/userFeedback")
@Slf4j
public class UserFeedbackController {
    @Resource
    private UserFeedbackService userFeedbackService;

    /**
     * 新增用户反馈
     *
     * @param userFeedbackDTO
     * @return
     */
    @PostMapping("/addUserFeedback")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("新增用户反馈")
    @Loggable(value = "新增用户反馈")
    public Result addUserFeedback(@RequestBody UserFeedbackDTO userFeedbackDTO) {
        log.info("新增用户反馈：{},{}", UserIdContext.getId(), userFeedbackDTO);
        userFeedbackService.addUserFeedback(userFeedbackDTO);
        return Result.success();
    }

    /**
     * 分页查询用户反馈记录
     *
     * @param page
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/pageQueryUserFeedback")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("分页查询用户反馈记录")
    @Loggable(value = "分页查询用户反馈记录")
    public Result pageQueryUserFeedBack(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginTime,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        log.info("分页查询用户反馈记录：{},{},{},{},{}", UserIdContext.getId(), page,pageSize,beginTime,endTime);
        PageResult pageResult =userFeedbackService.pageQueryUserFeedBack(page,pageSize,beginTime,endTime);
        return Result.success(pageResult);
    }

    /**
     * 批量删除用户反馈
     *
     * @param idListDTO
     * @return
     */
    @DeleteMapping("/batchDeleteFeedback")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("批量删除用户反馈")
    @Loggable(value = "批量删除用户反馈")
    public Result batchDeleteUserFeedBack(@RequestBody IdListDTO idListDTO) {
        log.info("批量删除用户反馈：{},{}", UserIdContext.getId(), idListDTO);
        userFeedbackService.batchDeleteUserFeedBack(idListDTO);
        return Result.success();
    }

}
