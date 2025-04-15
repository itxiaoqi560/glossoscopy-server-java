package com.tcm.glossoscopy.controller;


import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "用户信息接口")
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据用户id修改用户基础信息
     *
     * @param userDTO
     * @return
     */
    @PutMapping("/updateUserBasicInfo")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("修改用户基础信息")
    @Loggable(value = "修改用户基础信息")
    public Result updateUserBasicInfo(@RequestBody UserDTO userDTO) {
        log.info("修改用户基础信息：{},{}", UserIdContext.getId(), userDTO);
        userService.updateUserBasicInfo(userDTO);
        return Result.success();
    }

    /**
     * 修改用户手机号
     *
     * @param userDTO
     * @return
     */
    @PutMapping("/updateUserPhoneNumber")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("修改用户手机号")
    @Loggable(value = "修改用户手机号")
    public Result updateUserPhoneNumber(@RequestBody UserDTO userDTO) {
        log.info("修改用户手机号：{},{}", UserIdContext.getId(), userDTO);
        userService.updateUserPhoneNumber(userDTO);
        return Result.success();
    }

    /**
     * 修改用户私密信息
     *
     * @param userDTO
     * @return
     */
    @PutMapping("/updateUserPrivateInfo")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("修改用户私密信息")
    @Loggable(value = "修改用户私密信息")
    public Result updateUserPrivateInfo(@RequestBody UserDTO userDTO) {
        log.info("修改用户私密信息：{},{}", UserIdContext.getId(), userDTO);
        userService.updateUserPrivateInfo(userDTO);
        return Result.success();
    }

    /**
     * 校验用户账号是否存在
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/checkUserAccount")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("校验用户账号是否存在")
    @Loggable(value = "校验用户账号是否存在")
    public Result checkUserAccount(@RequestBody UserDTO userDTO) {
        log.info("校验用户账号是否存在：{},{}", UserIdContext.getId(), userDTO);
        userService.checkUserAccount(userDTO);
        return Result.success();
    }

    /**
     * 用户注销
     *
     * @param userDTO
     * @return
     */
    @DeleteMapping("/deregister")
    @PreAuthorize("hasAuthority('TCM:USER')")
    @ApiOperation("用户注销")
    @Loggable(value = "用户注销")
    public Result deregister(@RequestBody UserDTO userDTO) {
        log.info("用户注销：{}", UserIdContext.getId());
        userService.deregister(userDTO);
        return Result.success();
    }

}
