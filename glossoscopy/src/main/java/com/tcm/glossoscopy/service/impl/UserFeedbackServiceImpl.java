package com.tcm.glossoscopy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tcm.glossoscopy.context.UserIdContext;
import com.tcm.glossoscopy.entity.dto.IdListDTO;
import com.tcm.glossoscopy.entity.dto.UserFeedbackDTO;
import com.tcm.glossoscopy.entity.po.User;
import com.tcm.glossoscopy.entity.po.UserFeedback;
import com.tcm.glossoscopy.entity.result.PageResult;
import com.tcm.glossoscopy.entity.vo.UserFeedbackVO;
import com.tcm.glossoscopy.mapper.UserFeedbackMapper;
import com.tcm.glossoscopy.service.UserFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements UserFeedbackService {
    @Resource
    private UserFeedbackMapper userFeedbackMapper;
    @Override
    public void addUserFeedback(UserFeedbackDTO userFeedbackDTO) {
        UserFeedback userFeedback=new UserFeedback();
        BeanUtil.copyProperties(userFeedbackDTO,userFeedback,true);
        userFeedback.setDeleteFlag(false);
        userFeedback.setUserId(UserIdContext.getId());
        userFeedback.setCreateTime(LocalDateTime.now());
        userFeedbackMapper.addUserFeedBack(userFeedback);
    }

    @Override
    public void batchDeleteUserFeedBack(IdListDTO idListDTO) {
        userFeedbackMapper.batchDeleteById(idListDTO.getIdList(), UserIdContext.getId());
    }

    @Override
    public PageResult pageQueryUserFeedBack(Integer page, Integer pageSize, LocalDate beginTime, LocalDate endTime) {
        PageHelper.startPage(page,pageSize);
        endTime=endTime.plusDays(1);
        List<UserFeedback> userFeedbackList=userFeedbackMapper.pageQueryUserFeedback(beginTime,endTime,UserIdContext.getId());
        if(CollUtil.isEmpty(userFeedbackList)){
            return new PageResult<>(0L, Collections.emptyList());
        }
        Page<UserFeedback> userFeedbackPage=(Page<UserFeedback>) userFeedbackList;
        userFeedbackList=userFeedbackPage.getResult();
        List<UserFeedbackVO> userFeedbackVOList = userFeedbackList.stream()
                .map((userFeedback -> BeanUtil.copyProperties(userFeedback, UserFeedbackVO.class)))
                .collect(Collectors.toList());
        return new PageResult(userFeedbackPage.getTotal(),userFeedbackVOList);
    }
}
