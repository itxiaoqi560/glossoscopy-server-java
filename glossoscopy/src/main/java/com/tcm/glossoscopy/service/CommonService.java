package com.tcm.glossoscopy.service;

import com.tcm.glossoscopy.entity.dto.UserDTO;
import com.tcm.glossoscopy.entity.vo.CaptchaVO;
import com.tcm.glossoscopy.entity.vo.RecordDetailVO;
import com.tcm.glossoscopy.entity.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    /**
     * 获取手机动态码
     *
     * @param phoneNumber
     */
    void getPhoneCode(String phoneNumber);


    /**
     * 根据游客上传图片进行舌诊
     *
     * @param url
     * @return
     */
    RecordDetailVO diagnose(String url);


    /**
     * 保存用户上传的图片
     *
     * @param file
     * @return
     */
    String saveImage(MultipartFile file);


    /**
     * 询问AI中医有关问题
     *
     * @param question
     * @return
     */
    String callAI(String question);

    /**
     * 获取校验码
     * @return
     */
    CaptchaVO getCaptcha();

    /**
     * 账号登录
     * @param userDTO
     * @return
     */
    UserVO loginByAccount(UserDTO userDTO);

}
