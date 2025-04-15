package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.code.kaptcha.Producer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.bo.LoginUser;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.po.Record;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.vo.CaptchaVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.entity.vo.UserVO;
import com.tcm.glossoscopy.enums.*;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.mq.PhoneCodeMessageQueue;
import com.tcm.glossoscopy.service.CommonService;
import com.tcm.glossoscopy.utils.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private AliOssUtil aliOssUtil;
    @Resource
    private HealthStatusUtil healthStatusUtil;
    @Resource
    private HttpClientUtil httpClientUtil;
    @Resource
    private PhoneCodeMessageQueue phoneCodeMessageQueue;
    @Resource
    private RedisCache redisCache;
    @Resource
    private DeepSeekUtil deepSeekUtil;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 获取手机动态码
     *
     * @param phoneNumber
     */
    @Override
    public void getPhoneCode(String phoneNumber) {
        //校验手机号是否合法
        RegexUtil.isPhoneNumberValid(phoneNumber);
        try {
            phoneCodeMessageQueue.produce(phoneNumber);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.GET_DYNAMIC_CODE_FAILED);
        }
    }

//    @Resource
//    private RocketMessageQueue rocketMessageQueue;
//    /**
//     * 获取手机动态码
//     *
//     * @param phoneNumber
//     */
//    @Override
//    public void getPhoneCode(String phoneNumber) {
//        //校验手机号是否合法
//        RegexUtil.isPhoneNumberValid(phoneNumber);
//        try {
//            rocketMessageQueue.produce(phoneNumber);
//        } catch (Exception e) {
//            throw new BusinessException(ExceptionEnum.GET_DYNAMIC_CODE_FAILED);
//        }
//    }

    /**
     * 根据游客上传图片进行舌诊
     *
     * @param url
     * @return
     */
    @Override
    @SentinelResource(value = "myResource")
    public RecordDetailVO diagnose(String url) {
        //发送http请求获取成员详细诊断记录
        Record record = httpClientUtil.getRecordBySendHttpRequest(url);
        //获取详细诊断记录
        RecordDetailVO recordDetailVO = new RecordDetailVO();
        try {
            BeanUtil.copyProperties(record, recordDetailVO, Constant.HEALTH_STATUS);
            //设置当前诊断记录对应体质的详细信息
            recordDetailVO.setHealthStatus(healthStatusUtil.getHealthStatusById(record.getHealthStatus().getValue().longValue()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return recordDetailVO;
    }

    /**
     * 保存用户上传的图片
     *
     * @param file
     * @return
     */
    @Override
    public String saveImage(MultipartFile file) {
        //构建文件上传云端地址及文件名
        String fileName = file.getOriginalFilename();
        String extendName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + extendName;
        String url;
        try {
            //将文件上传至阿里云对象存储
            url = aliOssUtil.upload(file.getBytes(), fileName);
        } catch (IOException e) {
            throw new BusinessException(ExceptionEnum.FILE_UPLOAD_FAILED);
        }
        return url;
    }

    /**
     * 询问AI中医有关问题
     *
     * @param question
     * @return
     */
    @Override
    public String callAI(String question) {
        String reallyQuestion = question + "，请从中医的角度出发回答以上问题。";
        JsonObject response = deepSeekUtil.callDeepSeekApi(reallyQuestion);
        try {
            JsonArray choices = response.getAsJsonArray("choices");
            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            JsonObject message = firstChoice.getAsJsonObject("message");
            String answer = message.get("content").getAsString();
            return answer;
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.THE_SERVER_IS_BUSY);
        }
    }

    /**
     * 获取校验码
     *
     * @return
     */
    @Override
    public CaptchaVO getCaptcha() {
        // 保存校验码信息
        String uuid = cn.hutool.core.lang.UUID.randomUUID().toString(true);
        // 生成校验码
        String capText = captchaProducerMath.createText();
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);
        BufferedImage image = captchaProducerMath.createImage(capStr);
        redisCache.save(RedisConstant.CAPTCHA_KEY + uuid, code, RedisConstant.CAPTCHA_EXPIRE, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new BusinessException(ExceptionEnum.CAPTCHA_GENERATION_FAILED);
        }
        CaptchaVO captchaVO = CaptchaVO.builder()
                .captcha(Base64.getEncoder().encodeToString(os.toByteArray()))
                .uuid(uuid)
                .build();
        return captchaVO;
    }

    @Resource
    //先在SecurityConfig，使用@Bean注解重写官方的authenticationManagerBean类，然后这里才能注入成功
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 账号登录
     *
     * @param userDTO
     * @return
     */
    @Override
    //ResponseResult和user是我们在domain目录写好的类
    public UserVO loginByAccount(UserDTO userDTO) {
        RegexUtil.isAccountValid(userDTO.getAccount());
        RegexUtil.isPasswordValid(userDTO.getPassword());
        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getAccount(),userDTO.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        //判断用户身份信息是否合法
        if (Objects.isNull(authenticate)) {
            //账号或密码错误，用户身份不合法，返回错误信息
            throw new BusinessException(ExceptionEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        //缓存用户登录状态
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisCache.save(RedisConstant.LOGIN_KEY + id, loginUser, RedisConstant.LOGIN_EXPIRE, TimeUnit.DAYS);
        User user = loginUser.getUser();
        //添加用户id至ThreadLocal，方便后续生成jwt令牌
        UserIdContext.setId(user.getId());
        //用户身份合法，封装VO对象
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO, true);
        userVO.setIsFirstLogin(passwordEncoder.matches(Constant.RAW_PASSWORD, user.getPassword()));
        return userVO;
    }

}
