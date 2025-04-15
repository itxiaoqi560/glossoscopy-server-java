package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.UserReviewDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.service.UserReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户评价接口")
@RequestMapping("/api/userReview")
@Slf4j
public class UserReviewController {

    @Resource
    private UserReviewService userReviewService;

    /**
     * 新增用户评价
     *
     * @param userReviewDTO
     * @return
     */
    @PostMapping("/addUserReview")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("新增用户评价")
    @Loggable(value = "新增用户评价")
    public Result addUserReview(@RequestBody UserReviewDTO userReviewDTO) {
        log.info("新增用户评价：{},{}", UserIdContext.getId(), userReviewDTO);
        userReviewService.addUserReview(userReviewDTO);
        return Result.success();
    }

}
