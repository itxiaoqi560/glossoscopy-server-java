package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.bo.LoginUser;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.po.UserRole;
import com.tcm.glossoscopy.entity.vo.UserVO;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.mapper.*;
import com.tcm.glossoscopy.service.UserService;
import com.tcm.glossoscopy.token.SmsCodeAuthenticationToken;
import com.tcm.glossoscopy.utils.RegexUtil;
import com.tcm.glossoscopy.utils.TimeUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    //先在SecurityConfig，使用@Bean注解重写官方的authenticationManagerBean类，然后这里才能注入成功
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MenuMapper menuMapper;

    /**
     * 用户注销
     *
     * @param userDTO
     */
    @Transactional
    @Override
    public void deregister(UserDTO userDTO) {
        //校验用户身份是否合法
        checkPhoneCode(userDTO.getPhoneNumber(), userDTO.getPhoneCode());
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (!loginUser.getUser().getPhoneNumber().equals(userDTO.getPhoneNumber())) {
            //用户身份不合法
            return;
        }
        //用户身份合法，开始注销账号
        //批量删除用户成员的诊断记录
        recordMapper.batchDeleteByUserId(UserIdContext.getId());
        //批量删除用户成员
        memberMapper.batchDeleteByUserId(UserIdContext.getId());
        //删除用户信息
        userMapper.deleteById(UserIdContext.getId());
        redisCache.delete(RedisConstant.LOGIN_KEY + UserIdContext.getId());
    }

    /**
     * 根据用户id修改用户基础信息
     *
     * @param userDTO
     */
    @Override
    public void updateUserBasicInfo(UserDTO userDTO) {
        //修改用户信息
        User user = User.builder()
                .username(userDTO.getUsername())
                .avatar(userDTO.getAvatar())
                .updateTime(LocalDateTime.now())
                .id(UserIdContext.getId())
                .build();
        userMapper.updateInfoById(user);
        cacheLoginUserById();
    }

    /**
     * 修改用户手机号
     *
     * @param userDTO
     */
    public void updateUserPhoneNumber(UserDTO userDTO) {
        //校验用户身份是否合法
        checkPhoneCode(userDTO.getPhoneNumber(), userDTO.getPhoneCode());
        //校验用户手机号是否已经注册
        User user = userMapper.getUser(
                User.builder()
                        .phoneNumber(userDTO.getPhoneNumber())
                        .build());
        if (!Objects.isNull(user)) {
            //手机号已经注册过账号
            throw new BusinessException(ExceptionEnum.PHONE_NUMBER_ALREADY_REGISTERED);
        }
        //用户身份合法，开始修改用户信息
        user = User.builder()
                .id(UserIdContext.getId())
                .updateTime(LocalDateTime.now())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        userMapper.updateInfoById(user);
        cacheLoginUserById();
    }

    /**
     * 根据手机号修改用户私密信息
     *
     * @param userDTO
     */
    @Override
    public void updateUserPrivateInfo(UserDTO userDTO) {
        //校验账号是否合法
        if(!Objects.isNull(userDTO.getPassword())){
            RegexUtil.isAccountValid(userDTO.getAccount());
        }
        //校验密码是否合法
        if(!Objects.isNull(userDTO.getPassword())){
            RegexUtil.isPasswordValid(userDTO.getPassword());
        }
        //判断用户是否是第一次登录
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (isFirstLogin(loginUser.getPassword())) {
            //用户是第一次登录，修改用户密码
            User user = User.builder()
                    .id(UserIdContext.getId())
                    .updateTime(LocalDateTime.now())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();
            userMapper.updateInfoById(user);
            cacheLoginUserById();
            return;
        }
        //校验用户身份是否合法
        checkPhoneCode(userDTO.getPhoneNumber(), userDTO.getPhoneCode());
        if (!loginUser.getUser().getPhoneNumber().equals(userDTO.getPhoneNumber())) {
            //用户身份不合法
            return;
        }
        //用户身份合法，开始封装用户信息
        User user = User.builder()
                .account(userDTO.getAccount())
                .password(userDTO.getPassword())
                .id(UserIdContext.getId())
                .updateTime(LocalDateTime.now())
                .deleteFlag(false)
                .build();
        if (Strings.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.updateInfoById(user);
        cacheLoginUserById();
    }

    /**
     * 校验用户账号是否已存在
     *
     * @param userDTO
     */
    @Override
    public void checkUserAccount(UserDTO userDTO) {
        //校验账号是否合法
        RegexUtil.isAccountValid(userDTO.getAccount());
        //校验用户账号是否已存在
        User user = userMapper.getUser(
                User.builder()
                        .account(userDTO.getAccount())
                        .build());
        if (!Objects.isNull(user)) {
            throw new BusinessException(ExceptionEnum.ACCOUNT_ALREADY_EXIST);
        }
    }

    /**
     * 刷新令牌时间
     *
     * @return
     */
    @Override
    public void refreshToken() {
        redisCache.expire(RedisConstant.LOGIN_KEY + UserIdContext.getId(), RedisConstant.LOGIN_EXPIRE, TimeUnit.DAYS);
    }

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
        //验证校验码是否正确
        checkCaptcha(userDTO.getUuid(), userDTO.getCaptcha());
        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getAccount(),userDTO.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        return checkAuthentication(authentication);
    }

    /**
     * 用户退出登录
     */
    @Override
    public void logout() {
        //根据用户id，删除redis中的token值
        Long id = UserIdContext.getId();
        redisCache.delete(RedisConstant.LOGIN_KEY + id);
    }

    /**
     * 短信登录
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserVO loginBySms(UserDTO userDTO) {
        RegexUtil.isPhoneNumberValid(userDTO.getPhoneNumber());
        //用户在登录页面输入的用户名和密码
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(userDTO.getPhoneNumber(),userDTO.getPhoneCode());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authentication;
            authentication = authenticationManager.authenticate(smsCodeAuthenticationToken);
        return checkAuthentication(authentication);
    }

    private UserVO checkAuthentication(Authentication authentication){
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(!loginUser.getUser().getStatus()){
            throw new BusinessException(ExceptionEnum.ACCOUNT_IS_ABNORMAL);
        }
        User user=loginUser.getUser();
        //缓存用户登录状态
        Long id = loginUser.getUser().getId();
        redisCache.save(RedisConstant.LOGIN_KEY + id, loginUser, RedisConstant.LOGIN_EXPIRE, TimeUnit.DAYS);
        //添加用户id至ThreadLocal，方便后续生成jwt令牌
        UserIdContext.setId(user.getId());
        //用户身份合法，封装VO对象
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO, true);
        userVO.setIsFirstLogin(isFirstLogin(user.getPassword()));
        return userVO;
    }

    /**
     * 根据用户id缓存用户登录状态
     */
    private void cacheLoginUserById() {
        User user = userMapper.getUser(User.builder().id(UserIdContext.getId()).build());
        //封装用户权限
        List<String> permissionList = menuMapper.getPermissionByUserId(user.getId());
        //返回用户信息
        LoginUser loginUser = new LoginUser(user, permissionList);
        //缓存用户登录状态
        redisCache.save(RedisConstant.LOGIN_KEY + user.getId(), loginUser, RedisConstant.LOGIN_EXPIRE, TimeUnit.DAYS);

    }

    /**
     * 判断用户是否是第一次登录
     *
     * @param password
     * @return
     */
    private Boolean isFirstLogin(String password) {
        return passwordEncoder.matches(Constant.RAW_PASSWORD, password);
    }

    /**
     * 校验动态码
     *
     * @param phoneNumber
     * @param phoneCode
     */
    private void checkPhoneCode(String phoneNumber, String phoneCode) {
        //redis缓存获取手机动态码校验用户身份是否合法
        String key = RedisConstant.PHONE_CODE_PREFIX_KEY + phoneNumber;
        String phoneCode2 = redisCache.get(key, String.class);
        //校验用户身份是否合法
        if (!Strings.hasText(phoneCode2) || !phoneCode.equals(phoneCode2)) {
            //动态码错误，用户身份不合法，返回错误信息
            throw new BusinessException(ExceptionEnum.DYNAMIC_CODE_ERROR);
        }
        //用户身份合法
        redisCache.delete(key);
    }

    /**
     * 验证校验码是否合法
     *
     * @param uuid
     * @param captcha
     */
    private void checkCaptcha(String uuid, String captcha) {
        String captcha2 = redisCache.get(RedisConstant.CAPTCHA_KEY + uuid, String.class);
        redisCache.delete(RedisConstant.CAPTCHA_KEY + uuid);
        if (!Strings.hasText(captcha2) || !captcha2.equals(captcha)) {
            throw new BusinessException(ExceptionEnum.CAPTCHA_ERROR);
        }
    }
}
