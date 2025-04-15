package com.tcm.glossoscopy.service.impl;

import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.entity.bo.LoginUser;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.po.UserRole;
import com.tcm.glossoscopy.enums.ExceptionEnum;
import com.tcm.glossoscopy.exception.BusinessException;
import com.tcm.glossoscopy.mapper.UserMapper;
import com.tcm.glossoscopy.mapper.MenuMapper;
import com.tcm.glossoscopy.mapper.UserRoleMapper;
import com.tcm.glossoscopy.utils.TimeUtil;
import io.jsonwebtoken.lang.Strings;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户账号从数据库中查询用户信息和授权信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    //UserDetails是Security官方提供的接口
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户账号查询用户信息
        User user = userMapper.getUser(User.builder().account(username).build());
        //校验用户身份是否合法
        if (Objects.isNull(user)) {
            //用户身份不合法，返回错误信息
            throw new BusinessException(ExceptionEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        //封装用户权限
        List<String> permissionList = menuMapper.getPermissionByUserId(user.getId());
        //返回用户信息
        return new LoginUser(user, permissionList);
    }

    /**
     * 根据用户手机号登录并查询用户信息和授权信息
     *
     * @param phoneNumber
     * @param phoneCode
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserPhoneNumber(String phoneNumber, String phoneCode) throws UsernameNotFoundException {
        String key = RedisConstant.PHONE_CODE_PREFIX_KEY + phoneNumber;
        String phoneCode2 = redisCache.get(key, String.class);
        //校验用户身份是否合法
        if (!Strings.hasText(phoneCode2) || !phoneCode2.equals(phoneCode)) {
            //动态码错误，用户身份不合法，返回错误信息
            throw new BusinessException(ExceptionEnum.DYNAMIC_CODE_ERROR);
        }
        //用户身份合法
        redisCache.delete(key);
        //用户身份合法，开始注册或登录
        User user = userMapper.getUser(
                User.builder()
                        .phoneNumber(phoneNumber)
                        .build());
        //判断该手机号是否已经注册过用户
        if (!Objects.isNull(user)) {
            //该手机号已经注册过用户
            //封装用户权限
            List<String> permissionList = menuMapper.getPermissionByUserId(user.getId());
            //返回用户信息
            return new LoginUser(user, permissionList);
        }
        //手机号未注册用户，注册用户信息
        LocalDateTime nowTime = LocalDateTime.now();
        user = User.builder()
                .password(passwordEncoder.encode(Constant.RAW_PASSWORD))
                .phoneNumber(phoneNumber)
                .createTime(nowTime)
                .updateTime(nowTime)
                .status(true)
                .deleteFlag(false)
                .username(Constant.USER + TimeUtil.getMillInstance())
                .avatar(Constant.DEFAULT_USER_AVATAR)
                .account(Constant.TCM_PREFIX + TimeUtil.getMillInstance())
                .build();
        userMapper.addUser(user);
        //为新增的用户进行授权
        userRoleMapper.addUserRole(
                UserRole.builder()
                        .userId(user.getId())
                        .roleId(1L)
                        .build());
        //封装用户权限
        List<String> permissionList = menuMapper.getPermissionByUserId(user.getId());
        //返回用户信息
        return new LoginUser(user, permissionList);
    }
}