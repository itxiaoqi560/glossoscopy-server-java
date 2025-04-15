package com.tcm.glossoscopy.controller;

import com.tcm.glossoscopy.annotation.Loggable;
import com.tcm.glossoscopy.annotation.RateLimit;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.result.Result;
import com.tcm.glossoscopy.entity.vo.CaptchaVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.entity.vo.UserVO;
import com.tcm.glossoscopy.service.CommonService;
import com.tcm.glossoscopy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "通用接口")
@RequestMapping("/api/common")
@Slf4j
public class CommonController {

    @Resource
    private CommonService commonService;
    @Resource
    private UserService userService;

    /**
     * 获取手机动态码
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping("/getPhoneCode")
    @ApiOperation("获取手机动态码")
    @RateLimit(limit = 1,timeout = 60)
    @Loggable(value = "获取手机动态码")
    public Result getPhoneCode(String phoneNumber) {
        log.info("获取手机动态码：{}", phoneNumber);
        commonService.getPhoneCode(phoneNumber);
        return Result.success();
    }

    /**
     * 游客诊断
     *
     * @param url
     * @return
     */
    @GetMapping("/diagnose")
    @ApiOperation("游客诊断")
    @RateLimit(limit = 1,timeout = 60)
    @Loggable(value = "游客诊断")
    public Result diagnose(String url) {
        log.info("游客诊断：{}", url);
        RecordDetailVO recordDetailVO = commonService.diagnose(url);
        return Result.success(recordDetailVO);
    }

    /**
     * 保存图片
     *
     * @param file
     * @return
     */
    @PostMapping("/saveImage")
    @ApiOperation("保存图片")
    @RateLimit(limit = 2,timeout = 60)
    @Loggable(value = "保存图片")
    public Result saveImage(@RequestParam("file") MultipartFile file) {
        log.info("保存图片：{}", file);
        String url = commonService.saveImage(file);
        return Result.success(url);
    }

    /**
     * 询问AI中医有关问题
     *
     * @param question
     * @return
     */
    @GetMapping("/callAI")
    @ApiOperation("询问AI有关问题")
    @RateLimit(limit = 2,timeout = 60)
    @Loggable(value = "询问AI有关问题")
    public Result callAI(String question) {
        log.info("询问AI中医有关问题：{}", question);
        String answer = commonService.callAI(question);
        return Result.success(answer);
    }

    /**
     * 获取校验码
     *
     * @return
     */
    @GetMapping("/getCaptcha")
    @ApiOperation("获取校验码")
    @RateLimit(limit = 10,timeout = 60)
    @Loggable(value = "获取校验码")
    public Result getCaptcha() {
        log.info("获取校验码");
        CaptchaVO captchaVO = commonService.getCaptcha();
        return Result.success(captchaVO);
    }

    /**
     * 账号登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/loginByAccount")
    @ApiOperation("账号登录")
    @RateLimit(limit = 5,timeout = 600)
    @Loggable(value = "账号登录")
    public Result loginByAccount(@RequestBody UserDTO userDTO) {
        log.info("账号登录：{}", userDTO);
        UserVO userVO = commonService.loginByAccount(userDTO);
        return Result.successWithToken(userVO);
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
