package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.vo.UserVO;


public interface UserService extends IService<User> {

    /**
     * 账号登录
     * @param userDTO
     * @return
     */
    UserVO loginByAccount(UserDTO userDTO);

    /**
     * 短信登录
     * @param userDTO
     * @return
     */
    UserVO loginBySms(UserDTO userDTO);

    /**
     * 用户注销
     * @param userDTO
     */
    void deregister(UserDTO userDTO);

    /**
     * 根据用户id修改用户基础信息
     * @param userDTO
     */
    void updateUserBasicInfo(UserDTO userDTO);

    /**
     * 修改用户手机号
     * @param userDTO
     */
    void updateUserPhoneNumber(UserDTO userDTO);

    /**
     * 修改用户私密信息
     * @param userDTO
     */
    void updateUserPrivateInfo(UserDTO userDTO);

    /**
     * 校验用户账号是否已存在
     * @param userDTO
     */
    void checkUserAccount(UserDTO userDTO);

    /**
     * 刷新令牌时间
     *
     * @return
     */
    void refreshToken();

    /**
     * 用户退出登录
     */
    void logout();
}
