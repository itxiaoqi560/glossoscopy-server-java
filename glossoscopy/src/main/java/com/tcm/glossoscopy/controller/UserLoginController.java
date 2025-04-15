package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.annotation.RateLimit;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.UserVO;
import com.tcm.glossoscopy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户登录接口")
@RequestMapping("/api/login")
@Slf4j
public class UserLoginController {

    @Resource
    private UserService userService;

    /**
     * 账号登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/loginByAccount")
    @RateLimit(limit = 5,timeout = 600)
    @ApiOperation("账号登录")
    @Loggable(value = "账号登录")
    public Result loginByAccount(@RequestBody UserDTO userDTO) {
        log.info("账号登录：{}", userDTO);
        UserVO userVO = userService.loginByAccount(userDTO);
        return Result.successWithToken(userVO);
    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("用户退出登录")
    @Loggable(value = "用户退出登录")
    public Result logout() {
        log.info("用户退出登录：{}", UserIdContext.getId());
        userService.logout();
        return Result.success();
    }

    /**
     * 短信登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/loginBySms")
    @ApiOperation("短信登录")
    @RateLimit(limit = 5,timeout = 600)
    @Loggable(value = "短信登录")
    public Result loginBySms(@RequestBody UserDTO userDTO) {
        log.info("短信登录：{}", userDTO);
        UserVO userVO = userService.loginBySms(userDTO);
        return Result.successWithToken(userVO);
    }

}
