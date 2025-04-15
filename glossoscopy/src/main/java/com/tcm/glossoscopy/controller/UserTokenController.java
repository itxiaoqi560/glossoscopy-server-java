package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户登录凭证接口")
@RequestMapping("/api/token")
@Slf4j
public class UserTokenController {

    @Resource
    private UserService userService;

    /**
     * 刷新令牌时间
     *
     * @return
     */
    @GetMapping("/refreshToken")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("刷新令牌时间")
    @Loggable(value = "刷新令牌时间")
    public Result refreshToken() {
        log.info("刷新令牌时间：{}", UserIdContext.getId());
        userService.refreshToken();
        return Result.successWithToken();
    }
}
