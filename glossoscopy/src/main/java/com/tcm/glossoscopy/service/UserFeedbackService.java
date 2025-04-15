package com.tcm.glossoscopy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.UserFeedbackDTO;
import com.tcm.glossoscopy.entity.po.UserFeedback;
import com.tcm.glossoscopy.entity.result.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface UserFeedbackService extends IService<UserFeedback> {
    void addUserFeedback(UserFeedbackDTO userFeedbackDTO);

    void batchDeleteUserFeedBack(IdListDTO idListDTO);

    PageResult pageQueryUserFeedBack(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime);
}
